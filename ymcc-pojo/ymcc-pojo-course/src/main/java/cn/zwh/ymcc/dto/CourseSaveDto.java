package cn.zwh.ymcc.dto;

import cn.zwh.ymcc.domain.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSaveDto {
    @Valid
    private Course course;

    private CourseDetail courseDetail;

    private CourseMarket courseMarket;

    private CourseResource courseResource;
    @JsonProperty("teacharIds")
    private List<Long> TeacherIds;
}
