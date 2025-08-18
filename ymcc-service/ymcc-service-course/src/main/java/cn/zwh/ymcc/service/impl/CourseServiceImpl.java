package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.*;
import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.domain.*;
import cn.zwh.ymcc.dto.*;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.CourseMapper;
import cn.zwh.ymcc.service.*;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private ICourseDetailService courseDetailService;
    @Autowired
    private ICourseMarketService courseMarketService;
    @Autowired
    private ICourseResourceService courseResourceService;
    @Autowired
    private ICourseTeacherService courseTeacherService;
    @Autowired
    private SearchFeignAPI searchFeignAPI;
    @Autowired
    private ICourseSummaryService courseSummaryService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PublishFeignAPI publishFeignAPI;
    @Override
    public void save(CourseSaveDto courseSaveDto) {
        Course course = courseSaveDto.getCourse();
        CourseDetail courseDetail = courseSaveDto.getCourseDetail();
        CourseMarket courseMarket = courseSaveDto.getCourseMarket();
        CourseResource courseResource = courseSaveDto.getCourseResource();
        List<Long> teacherIds = courseSaveDto.getTeacherIds();

        //1.校验 非空校验 略

        //2.保存
        //course
        List<Teacher> teachers = teacherService.selectBatchIds(teacherIds);
        course.setTeacherNames(teachers.stream().map(Teacher::getName).collect(Collectors.joining(",")));
        course.setStatus(Course.ONLINE);
        insert(course);

        //courseDetail
        courseDetail.setId(course.getId());
        courseDetailService.insert(courseDetail);

        //courseMarket
        courseMarket.setId(course.getId());
        courseMarketService.insert(courseMarket);

        //courseResource
        courseResource.setCourseId(course.getId());
        courseResourceService.insert(courseResource);

        //courseTeacher
        List<CourseTeacher> courseTeachers = new ArrayList<>();
        teacherIds.forEach(teacherId -> {
            CourseTeacher courseTeacher = new CourseTeacher();
            courseTeacher.setCourseId(course.getId());
            courseTeacher.setTeacherId(teacherId);
            courseTeachers.add(courseTeacher);
        });
        courseTeacherService.insertBatch(courseTeachers);
    }

    @Override
    public void onLineCourse(List<Long> courseIds) {
        //1.参数校验
        if (courseIds == null || courseIds.size() == 0) {
            throw new GlobleBusinessException("没有选择课程");
        }
        //2.查询是否上线
        List<Course> courses = selectBatchIds(courseIds);
        courses.forEach(course -> {
            if (course.getStatus() == Course.ONLINE) {
                throw new GlobleBusinessException(course.getName()+"课程已上线");
            }
        });
        //3.更新课程信息
        courses.forEach(course -> {
            course.setStatus(Course.ONLINE);
            course.setOnlineTime(new Date());
            updateById(course);
        });
        //准备一个list
        List<CourseDoc> courseDocs = new ArrayList<>();
        courses.forEach(course -> {
            CourseDoc courseDoc = new CourseDoc();
            BeanUtils.copyProperties(course, courseDoc);
            courseDoc.setTeacherName(course.getTeacherNames());
            CourseMarket courseMarket = courseMarketService.selectById(course.getId());
            BeanUtils.copyProperties(courseMarket, courseDoc);

            //查询 t_course_summary
            CourseSummary courseSummary = courseSummaryService.selectById(course.getId());
            BeanUtils.copyProperties(courseSummary, courseDoc);

            courseDocs.add(courseDoc);
        });
        //4.运程调用搜索服务，添加到es中
        searchFeignAPI.save(courseDocs);
        publishMessage();
    }

    public void publishMessage(){
        //1.准备消息内容
        MessageStationDto messageStationDto = new MessageStationDto();
        messageStationDto.setTitle("课程发布");
        messageStationDto.setContent("尊敬的用户，您等待已久的xxyy课程已上线...");
        messageStationDto.setType("系统消息");
        //远程调用user服务，查询用户id
        List<Long> userIds = publishFeignAPI.findAll().stream().map(User::getId).collect(Collectors.toList());
        messageStationDto.setUserIds(userIds);
        //发送站内消息
        SendResult sendResult = rocketMQTemplate.syncSend(BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC +
                        ":" + BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_STATION,
                MessageBuilder.withPayload(JSONObject.toJSONString(messageStationDto)).build());
        log.info("发送站内消息结果为:{}",sendResult);


        //准备短信消息内容 假的
        MessageSMSDto messageSMSDto = new MessageSMSDto();
        messageSMSDto.setTitle("课程发布");
        messageSMSDto.setContent("尊敬的用户，您等待已久的xxyy课程已上线...");

        List<UserPhoneIpDto> userPhoneIpDtos = new ArrayList<>();
        publishFeignAPI.findAll().forEach(user -> {
            UserPhoneIpDto userPhoneIpDto = new UserPhoneIpDto();
            userPhoneIpDto.setIp("127.0.0.1");
            userPhoneIpDto.setUserId(user.getId());
            userPhoneIpDto.setPhone(user.getPhone());
            userPhoneIpDtos.add(userPhoneIpDto);
        });
        messageSMSDto.setUserPhoneIpDtos(userPhoneIpDtos);

        //发送站内消息
        SendResult sendResultSMS = rocketMQTemplate.syncSend(BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC +
                        ":" + BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_SMS,
                MessageBuilder.withPayload(JSONObject.toJSONString(messageSMSDto)).build());
        log.info("发送短信消息结果为:{}",sendResultSMS);

        //准备邮件消息内容 假的
        MessageEmailDto messageEmailDto = new MessageEmailDto();
        messageEmailDto.setTitle("课程发布");
        messageEmailDto.setContent("尊敬的用户，您等待已久的xxyy课程已上线...");
        List<UserEmailDto> userEmailDtos = new ArrayList<>();
        publishFeignAPI.findAll().forEach(user -> {
            UserEmailDto userEmailDto = new UserEmailDto();
            userEmailDto.setEmail(user.getEmail());
            userEmailDto.setUserId(user.getId());
            userEmailDtos.add(userEmailDto);
        });
        messageEmailDto.setUserEmailDtos(userEmailDtos);

        //发送邮箱消息
        SendResult sendResultEmail = rocketMQTemplate.syncSend(BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC +
                ":"+BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_EMAIL,
                MessageBuilder.withPayload(JSONObject.toJSONString(messageEmailDto)).build());
        log.info("发送短信消息结果为:{}",sendResultEmail);
    }
}
