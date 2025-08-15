package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.mapper.LoginMapper;
import cn.zwh.ymcc.service.ILoginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

}
