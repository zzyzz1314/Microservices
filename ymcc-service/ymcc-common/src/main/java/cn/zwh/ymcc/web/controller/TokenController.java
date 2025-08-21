package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @GetMapping("/createToken/{courseId}")
    public JSONResult getToken(@PathVariable("courseId") Long courseId){
        String token = tokenService.getToken(courseId);
        return JSONResult.success(token);
    }
}
