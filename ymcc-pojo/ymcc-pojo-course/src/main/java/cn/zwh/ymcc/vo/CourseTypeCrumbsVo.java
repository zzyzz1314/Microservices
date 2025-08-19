package cn.zwh.ymcc.vo;

import cn.zwh.ymcc.domain.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回给前端的课程类型面包屑数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTypeCrumbsVo {

    private CourseType ownerProductType; //本级类型

    private List<CourseType> otherProductTypes; //同级类型
}
