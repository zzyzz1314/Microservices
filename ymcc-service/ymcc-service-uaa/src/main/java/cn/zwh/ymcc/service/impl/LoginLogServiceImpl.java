package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.LoginLog;
import cn.zwh.ymcc.mapper.LoginLogMapper;
import cn.zwh.ymcc.service.ILoginLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
