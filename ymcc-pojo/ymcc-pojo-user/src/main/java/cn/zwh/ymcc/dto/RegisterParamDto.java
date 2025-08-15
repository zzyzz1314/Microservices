package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 手机号注册参数
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParamDto {
    //手机号
    private String mobile;
    //密码
    private String password;
    //注册渠道
    private Integer regChannel;
    //验证码
    private String smsCode;
}
