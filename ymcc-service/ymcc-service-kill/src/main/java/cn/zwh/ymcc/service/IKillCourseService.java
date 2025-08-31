package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.KillCourse;
import cn.zwh.ymcc.dto.KillDto;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-29
 */
public interface IKillCourseService extends IService<KillCourse> {

    /*
    * 添加秒杀课程
    * */
    void save(KillCourse killCourse);

    /*
    * 查询所有秒杀课程
    * */
    List<KillCourse> queryOnlineAll();

    /*
    * 查询一个正在秒杀的课程，redis
    * */
    KillCourse queryOnlineOne(Long activityId, Long courseId);

    /*
    * 秒杀
    * */
    String kill(KillDto killDto);
}
