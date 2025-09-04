package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.dto.LoginDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.LoginMapper;
import cn.zwh.ymcc.service.ILoginService;
import cn.zwh.ymcc.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

    @Value("${ymcc.authurl}")
    private String authUrl;

    @Override
    public Map<String, Object> login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        Integer type = loginDto.getType();

        Wrapper<Login> wrapper=new EntityWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("type", type);
        Login login = selectOne(wrapper);

        if (login == null){
            throw new GlobleBusinessException("用户不存在，请重试");
        }

        String format = String.format(authUrl, login.getClientId(), login.getClientSecret(), username, password);
        //log.info("登录地址：{}",format);
        String s = HttpUtil.sendPost(format, null);
        //log.info("登录返回结果：{}",s);
        return JSONObject.parseObject(s, Map.class);
    }
}
