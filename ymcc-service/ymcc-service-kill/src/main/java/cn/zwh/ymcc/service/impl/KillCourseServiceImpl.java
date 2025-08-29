package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.KillActivity;
import cn.zwh.ymcc.domain.KillCourse;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.KillCourseMapper;
import cn.zwh.ymcc.service.IKillActivityService;
import cn.zwh.ymcc.service.IKillCourseService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-29
 */
@Service
public class KillCourseServiceImpl extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {

    @Autowired
    private IKillActivityService killActivityService;

    @Override
    public void save(KillCourse killCourse) {
        //1.校验
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
}
