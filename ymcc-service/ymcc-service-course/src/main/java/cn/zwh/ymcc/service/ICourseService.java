package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.Course;
import cn.zwh.ymcc.dto.CourseInfoDto;
import cn.zwh.ymcc.dto.CourseSaveDto;
import cn.zwh.ymcc.vo.CourseDetailDataVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
public interface ICourseService extends IService<Course> {

    /**
     * 保存课程信息
     * @param courseSaveDto
     */
    void save(CourseSaveDto courseSaveDto);

    /**
     * 上线课程
     * @param courseIds
     */
    void onLineCourse(List<Long> courseIds);

    /**
     * 课程查询
     * @param courseId
     * @return
     */
    CourseDetailDataVo detail(Long courseId);

    /**
     * 课程查询 订单相关
     * @param courseIds
     * @return
     */
    CourseInfoDto info(String courseIds);
}
