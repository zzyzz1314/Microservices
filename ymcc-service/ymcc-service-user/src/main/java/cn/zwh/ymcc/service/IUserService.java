package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.User;
import cn.zwh.ymcc.dto.RegisterParamDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */

//手机号注册
public interface IUserService extends IService<User> {

    void phoneRegister(RegisterParamDto registerParamDto);
}
