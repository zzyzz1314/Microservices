package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.CourseType;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.exception.GlobleExceptionHandler;
import cn.zwh.ymcc.mapper.CourseTypeMapper;
import cn.zwh.ymcc.service.ICourseTypeService;
import cn.zwh.ymcc.vo.CourseTypeCrumbsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public List<CourseType> getTreeData() {
        //1.先从redis中获取数据
        Object o = redisTemplate.opsForValue().get(BusinessConstants.REDIS_COURSE_TYPE_DATA);
        //2.如果有数据，直接返回
        if (o != null){
            return (List<CourseType>) o;
        }
        //3.如果没有数据，则从数据库中获取数据
        List<CourseType> firstCourseTypes = getCourseTypes();
        //4.将数据保存到redis中
        redisTemplate.opsForValue().set(BusinessConstants.REDIS_COURSE_TYPE_DATA,firstCourseTypes);

        return firstCourseTypes;
    }

    @Override
    public List<CourseTypeCrumbsVo> crumbs(Long courseTypeId) {
        // 判断参数
        if(courseTypeId==null){
            throw new GlobleBusinessException("参数异常");

        }

        // 根据id查询
        CourseType courseType = selectById(courseTypeId);

        if(courseType==null){
            throw new GlobleBusinessException("参数异常");
        }

        // 获取path，
        String[] idStrs = courseType.getPath().split("\\.");

        List<CourseType> courseTypes = selectBatchIds(Arrays.asList(idStrs));

        List<CourseTypeCrumbsVo> courseTypeCrumbsVos = new ArrayList<>();

        courseTypes.forEach(type -> {
            EntityWrapper<CourseType> wrapper = new EntityWrapper<>();

            wrapper.eq("pid",type.getPid());
            wrapper.notIn("id",type.getId());
            List<CourseType> others = selectList(wrapper);

            courseTypeCrumbsVos.add(new CourseTypeCrumbsVo(type,others));
        });

        return courseTypeCrumbsVos;
    }

    private List<CourseType> getCourseTypes() {
        List<CourseType> allCourseTypes = selectList(null);

        HashMap<Long, CourseType> allCourseTypeMap = new HashMap<>();
        for (CourseType tmp : allCourseTypes) {
            allCourseTypeMap.put(tmp.getId(), tmp);
        }

        // 第一层课程分类
        List<CourseType> firstCourseTypes = new ArrayList<>();

        for (CourseType courseTypeTmp : allCourseTypes) {
            if (courseTypeTmp.getPid() == 0 || courseTypeTmp.getPid() == null) {
                firstCourseTypes.add(courseTypeTmp);
            } else {
                CourseType parent = allCourseTypeMap.get(courseTypeTmp.getPid());
                if (parent != null) {
                    parent.getChildren().add(courseTypeTmp);
                }
            }
        }
        return firstCourseTypes;
    }

    @Override
    public boolean insert(CourseType entity) {
        redisTemplate.delete(BusinessConstants.REDIS_COURSE_TYPE_DATA);
        return super.insert(entity);
    }

    @Override
    public boolean deleteById(Serializable id) {
        redisTemplate.delete(BusinessConstants.REDIS_COURSE_TYPE_DATA);
        return super.deleteById(id);
    }

    @Override
    public boolean updateById(CourseType entity) {
        redisTemplate.delete(BusinessConstants.REDIS_COURSE_TYPE_DATA);
        return super.updateById(entity);
    }
}
