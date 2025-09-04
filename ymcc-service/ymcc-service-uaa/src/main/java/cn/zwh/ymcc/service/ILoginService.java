package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.dto.LoginDto;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 登录表 服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
public interface ILoginService extends IService<Login> {

    /**
     * 登录,返回 token
     * @param loginDto
     * @return
     */
    Map<String, Object> login(LoginDto loginDto);
}
