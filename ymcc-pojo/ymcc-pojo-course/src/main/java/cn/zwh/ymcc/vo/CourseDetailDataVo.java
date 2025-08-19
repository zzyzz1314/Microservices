package cn.zwh.ymcc.vo;

import cn.zwh.ymcc.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailDataVo {

    private Course course;

    //营销
    private CourseMarket courseMarket;

    //课程对应的章节
    private List<CourseChapter> courseChapters;

    //老师
    private List<Teacher> teachers;

    //课程详情
    private CourseDetail courseDetail;

    //课程总结
    private CourseSummary courseSummary;
}
