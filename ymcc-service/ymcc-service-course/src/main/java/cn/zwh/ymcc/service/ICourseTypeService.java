package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.CourseType;
import cn.zwh.ymcc.vo.CourseTypeCrumbsVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
public interface ICourseTypeService extends IService<CourseType> {

    List<CourseType> getTreeData();

    /*
    * 面包屑
    * */
    List<CourseTypeCrumbsVo> crumbs(Long courseTypeId);
}
