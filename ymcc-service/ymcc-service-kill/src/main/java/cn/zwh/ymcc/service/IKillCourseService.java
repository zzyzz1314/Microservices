package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.KillCourse;
import com.baomidou.mybatisplus.service.IService;

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
}
