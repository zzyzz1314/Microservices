package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.CourseTeacher;
import cn.zwh.ymcc.domain.Teacher;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 课程和老师的中间表 服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
public interface ICourseTeacherService extends IService<CourseTeacher> {

    List<Teacher> selectTeachersByCourseId(Long courseId);
}
