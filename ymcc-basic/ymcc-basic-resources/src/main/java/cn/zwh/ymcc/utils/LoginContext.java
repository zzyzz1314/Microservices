package cn.zwh.ymcc.utils;

import cn.zwh.ymcc.domain.Login;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginContext {
    public static Login getLogin() {
        String string = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Login login = JSONObject.parseObject(string, Login.class);
        return login;
    }
}
