package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.KillActivity;
import cn.zwh.ymcc.domain.KillCourse;
import cn.zwh.ymcc.dto.KillActivityDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.KillActivityMapper;
import cn.zwh.ymcc.service.IKillActivityService;
import cn.zwh.ymcc.service.IKillCourseService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-29
 */
@Service
public class KillActivityServiceImpl extends ServiceImpl<KillActivityMapper, KillActivity> implements IKillActivityService {

    @Autowired
    private IKillCourseService killCourseService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void save(KillActivityDto killActivityDto) {
        KillActivity killActivity = new KillActivity();
        killActivity.setName(killActivityDto.getName());
        killActivity.setBeginTime(killActivityDto.getBeginTime().getTime());
        killActivity.setEndTime(killActivityDto.getEndTime().getTime());
        killActivity.setPublishStatus(KillActivity.PUBLISH_STATUS_WAIT);
        killActivity.setCreateTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(killActivityDto.getBeginTime());
        killActivity.setTimeStr(format);
        insert(killActivity);
    }

    @Override
    public void publish(Long id) {
        //1.校验

        KillActivity killActivity = selectById(id);
        //如果课程已经发布了，则直接提示该活动已发布了
        if (killActivity.getPublishStatus()==1){
            throw new GlobleBusinessException("该活动已发布");
        }

        //2.修改秒杀活动状态
        killActivity.setPublishStatus(KillActivity.PUBLISH_STATUS_SUCCESS);
        killActivity.setPublishTime(new Date());
        updateById(killActivity);

        //3.修改活动关联的课程状态
        EntityWrapper<KillCourse> wrapper = new EntityWrapper<>();
        wrapper.eq("activity_id", id);
        List<KillCourse> killCourses = killCourseService.selectList(wrapper);

        for (KillCourse killCours : killCourses) {
            killCours.setPublishStatus(KillCourse.PUBLISH_STATUS_SUCCESS);
            killCours.setPublishTime(new Date());
            killCourseService.updateById(killCours);

            //4.缓存预热，存入redis
            // hash中的大key 课程信息
            String hashKey = "activity:"+id;
            redisTemplate.opsForHash().put(hashKey,"course:"+killCours.getId(),killCours);

            //库存数量
            redisTemplate.opsForValue().set("course:"+killCours.getId(),killCours.getKillCount());

        }

    }

}
