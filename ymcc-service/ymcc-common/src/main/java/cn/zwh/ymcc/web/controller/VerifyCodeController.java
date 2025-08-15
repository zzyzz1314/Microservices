package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("verifycode")
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService verifyCodeService;
    @GetMapping("/sendSmsCode/{phone}")
    public JSONResult sendSmsCode(@PathVariable("phone") String phone) {
        verifyCodeService.sendSmsCode(phone);
        return JSONResult.success();
    }
}
