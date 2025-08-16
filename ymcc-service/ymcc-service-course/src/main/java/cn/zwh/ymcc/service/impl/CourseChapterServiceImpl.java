package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.CourseChapter;
import cn.zwh.ymcc.mapper.CourseChapterMapper;
import cn.zwh.ymcc.service.ICourseChapterService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {

    @Autowired
    private CourseChapterMapper courseChapterMapper;


    @Override
    public List<CourseChapter> listByCourseId(Long courseId) {
        Wrapper<CourseChapter> www=new EntityWrapper<CourseChapter>();
        www.eq("course_id",courseId);
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(www);
        return courseChapters;
    }
}
