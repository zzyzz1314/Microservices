package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.KillActivity;
import cn.zwh.ymcc.domain.KillCourse;
import cn.zwh.ymcc.dto.KillDto;
import cn.zwh.ymcc.dto.RedisOrderDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.KillCourseMapper;
import cn.zwh.ymcc.service.IKillActivityService;
import cn.zwh.ymcc.service.IKillCourseService;
import cn.zwh.ymcc.util.CodeGenerateUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-29
 */
@Slf4j
@Service
public class KillCourseServiceImpl extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {

    @Autowired
    private IKillActivityService killActivityService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void save(KillCourse killCourse) {
        //1.校验
        KillActivity killActivity1 = killActivityService.selectById(killCourse.getActivityId());
        if (killActivity1.getPublishStatus()==KillActivity.PUBLISH_STATUS_SUCCESS){
            throw new GlobleBusinessException("该活动已经发布,不能再添加课程了");
        }

        if (killCourse == null ||
                killCourse.getCourseName() == null ||
                killCourse.getCourseName().trim().length() == 0 ||
                killCourse.getCourseId() == null ||
                killCourse.getActivityId() == null ||
                killCourse.getKillCount() <= 0 ||
                killCourse.getKillPrice() == null ||
                killCourse.getKillPrice().compareTo(killCourse.getKillPrice()) < 0
        ) {
            throw new GlobleBusinessException("秒杀课程参数异常");
        }

        //判断该课程是否已经加入该秒杀了
        //根据活动id和课程id查询 t_kill_course
        Wrapper<KillCourse> wrapper = new EntityWrapper<>();
        wrapper.eq("activity_id", killCourse.getActivityId());
        wrapper.eq("course_id", killCourse.getCourseId());
        KillCourse killCourseForDB = selectOne(wrapper);
        if (killCourseForDB != null) {
            throw new GlobleBusinessException("该课程已经加入该秒杀");
        }

        //2.添加课程
        KillActivity killActivity = killActivityService.selectById(killCourse.getActivityId());

        killCourse.setKillLimit(1);
        killCourse.setKillSort(0);
        killCourse.setPublishStatus(KillCourse.PUBLISH_STATUS_WAIT);
        killCourse.setStartTime(killActivity.getBeginTime());
        killCourse.setEndTime(killActivity.getEndTime());
        killCourse.setCreateTime(new Date());
        killCourse.setTimeStr(killActivity.getTimeStr());
        insert(killCourse);

    }

    @Override
    public List<KillCourse> queryOnlineAll() {

        List<KillCourse> killCourses=new ArrayList<>();
        //查询所有活动发布的课程
        Set<Object> keys = redisTemplate.keys("activity:*");
        if (keys == null|| keys.size() == 0){
            return killCourses;
        }

        for (Object key : keys) {
            List values = redisTemplate.opsForHash().values(key);
            killCourses.addAll(values);
        }

        return killCourses;
    }

    @Override
    public KillCourse queryOnlineOne(Long activityId, Long courseId) {

        Object o = redisTemplate.opsForHash().get("activity:" + activityId, "course:" + courseId);
        if (o != null) {
            return (KillCourse) o;
        }else {
            throw new GlobleBusinessException("该课程不存在");
        }
    }

    @Override
    public String kill(KillDto killDto) {

        //假的登录
        Long loginId = 3l;

        //1.校验参数

        //2.判断数据（课程是否存在）
        Object o = redisTemplate.opsForHash().get("activity:" + killDto.getActivityId(), "course:" + killDto.getCourseId());
        if (o == null) {
            throw new GlobleBusinessException("该课程不存在,请下单其他课程");
        }
        KillCourse killCourse = (KillCourse) o;
        //3.判断当前时间是否大于结束时间
        Date now = new Date();
        if (now.getTime() > killCourse.getEndTime()) {
            throw new GlobleBusinessException("该课程秒杀已结束");
        }

        //4.减库存
        RSemaphore semaphore = redissonClient.getSemaphore("activity:" + killDto.getActivityId() + ":course:" +
                killDto.getCourseId());
        //-1库存
        boolean b = semaphore.tryAcquire(1);
        if (b){
            log.info("========哈哈哈哈哈哈哈哈哈哈哈哈哈============");
        }
        if (!b) {
            throw new GlobleBusinessException("该课程已售完,请下次光临");
        }

        //5.生成临时订单
        RedisOrderDto redisOrderDto = new RedisOrderDto();
        redisOrderDto.setOrderNo(CodeGenerateUtils.generateOrderSn(loginId));
        redisOrderDto.setTotalPrice(killCourse.getKillPrice());
        redisOrderDto.setLoginId(loginId);
        redisOrderDto.setCourseName(killCourse.getCourseName());
        redisOrderDto.setCourseId(killDto.getCourseId());
        redisOrderDto.setCoursePic(killCourse.getCoursePic());

        //存入redis中
        redisTemplate.opsForValue().set("redisOrder:" + redisOrderDto.getOrderNo(), redisOrderDto);

        return redisOrderDto.getOrderNo();
    }
}
