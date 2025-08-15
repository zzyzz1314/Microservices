package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.dto.CodeDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.service.VerifyCodeService;
import cn.zwh.ymcc.util.VerifyCodeUtils;
import cn.zwh.ymcc.utils.SendSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private SendSmsUtils sendSmsUtils;
    @Override
    public void sendSmsCode(String phone) {

    //1.校验手机号，正则
        if (StringUtils.isEmpty( phone)){
            throw new GlobleBusinessException("手机号不能为空");
        }
        String key= BusinessConstants.PHONE_REGISTER+phone;
//   3. 从redis中获取code
        Object obj = redisTemplate.opsForValue().get(key);
        CodeDto value=null;
//     3.1：获取到了
        if (obj!=null){
            value =(CodeDto)obj;
            String code = value.getCode();
//	     3.1.1： 判断是否超过一分钟，如果没有超过一分钟，提示用户 一分钟只能获取一次。
            if (new Date().getTime()-value.getTimer()<60*1000){
                throw new GlobleBusinessException("一分钟内只能获取一次验证码");
            }
        }else {
//          3.2： 没取到 生成一个随机的验证码
            String code = VerifyCodeUtils.generateVerifyCode(4);
            long timer = new Date().getTime();
            value = new CodeDto(code,timer);
        }
        log.info("手机验证码:{} 过期时间{}分钟",value.getCode(),5);
        //String s = sendSmsUtils.sendSms(phone, value.getCode());
        //System.out.println(s);
        redisTemplate.opsForValue().set(key,value,5, TimeUnit.MINUTES);
    }
}
