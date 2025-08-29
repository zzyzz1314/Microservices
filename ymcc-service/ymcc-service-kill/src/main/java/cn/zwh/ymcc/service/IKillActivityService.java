package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.KillActivity;
import cn.zwh.ymcc.dto.KillActivityDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-29
 */
public interface IKillActivityService extends IService<KillActivity> {

    /*
    * 添加秒杀活动
    * */
    void save(KillActivityDto killActivityDto);

    /*
    * 秒杀活动发布
    * */
    void publish(Long id);
}
