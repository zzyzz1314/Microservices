package cn.zwh.ymcc.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	//登录成功后重定向地址
    @RequestMapping("/loginSuccess")
    public Object loginSuccess(){

        // 获取当前的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        return authentication.getPrincipal();
	} 
}