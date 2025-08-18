package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.Course;
import cn.zwh.ymcc.dto.CourseSaveDto;
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
}
