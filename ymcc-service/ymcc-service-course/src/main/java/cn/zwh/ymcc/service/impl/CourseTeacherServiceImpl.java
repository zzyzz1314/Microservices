package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.CourseTeacher;
import cn.zwh.ymcc.mapper.CourseTeacherMapper;
import cn.zwh.ymcc.service.ICourseTeacherService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程和老师的中间表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements ICourseTeacherService {

}
