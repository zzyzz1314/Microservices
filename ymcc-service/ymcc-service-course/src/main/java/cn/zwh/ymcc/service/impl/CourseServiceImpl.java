package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.*;
import cn.zwh.ymcc.dto.CourseSaveDto;
import cn.zwh.ymcc.mapper.CourseMapper;
import cn.zwh.ymcc.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
}
