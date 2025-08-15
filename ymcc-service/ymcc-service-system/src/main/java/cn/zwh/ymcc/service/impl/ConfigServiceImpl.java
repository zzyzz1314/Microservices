package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.Config;
import cn.zwh.ymcc.mapper.ConfigMapper;
import cn.zwh.ymcc.service.IConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-11
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
