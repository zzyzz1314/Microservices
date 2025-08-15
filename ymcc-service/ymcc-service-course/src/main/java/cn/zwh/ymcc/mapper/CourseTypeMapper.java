package cn.zwh.ymcc.mapper;

import cn.zwh.ymcc.domain.CourseType;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程目录 Mapper 接口
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Mapper
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    @Select("select * from t_course_type")
    List<CourseType> selectAll();
}
