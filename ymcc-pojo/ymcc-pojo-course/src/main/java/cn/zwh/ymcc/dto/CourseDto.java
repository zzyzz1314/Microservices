package cn.zwh.ymcc.dto;

import cn.zwh.ymcc.domain.Course;
import cn.zwh.ymcc.domain.CourseMarket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Course course;
    private CourseMarket courseMarket;
}
