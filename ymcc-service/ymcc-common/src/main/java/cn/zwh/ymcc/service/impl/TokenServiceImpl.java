package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.service.TokenService;
import cn.zwh.ymcc.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public String getToken(Long courseId) {

        // 生成token
        String token = StrUtils.getComplexRandomString(32);
        //key 业务键:登录人员id:课程id
        Long loginId= 3l;
        String key = String.format(BusinessConstants.REDIS_PREVENT_REPEAT_SUBMIT_ORDER,loginId,courseId);
        redisTemplate.opsForValue().set(key,token,5, TimeUnit.MINUTES);
        return token;
    }
}
