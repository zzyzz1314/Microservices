package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.LoginFeignAPI;
import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.domain.User;
import cn.zwh.ymcc.domain.UserAccount;
import cn.zwh.ymcc.domain.UserBaseInfo;
import cn.zwh.ymcc.dto.CodeDto;
import cn.zwh.ymcc.dto.RegisterParamDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.UserMapper;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.IUserAccountService;
import cn.zwh.ymcc.service.IUserBaseInfoService;
import cn.zwh.ymcc.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private LoginFeignAPI loginFeignAPI;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserBaseInfoService userBaseInfoService;
    @Autowired
    private UserMapper userMapper;


    @Override
    @GlobalTransactional
    public void phoneRegister(RegisterParamDto registerParamDto) {
        // 分布式事务
        String phone = registerParamDto.getMobile();
        String password = registerParamDto.getPassword();
        String smsCode = registerParamDto.getSmsCode();
        Integer regChannel = registerParamDto.getRegChannel();
        //1.校验
        if (StringUtils.isEmpty(phone)){
            throw new GlobleBusinessException("手机号不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new GlobleBusinessException("密码不能为空");
        }
        if (StringUtils.isEmpty(smsCode)){
            throw new GlobleBusinessException("验证码不能为空");
        }
        if (regChannel==null){
            throw new GlobleBusinessException("注册渠道不能为空");
        }

        //2.校验手机号
        Wrapper<User> wrapper=new EntityWrapper<>();
        wrapper.eq("phone",phone);
        if (selectOne(wrapper)!=null){
            throw new GlobleBusinessException("手机号已经注册");
        }
        //3.校验验证码
        String key= BusinessConstants.PHONE_REGISTER+phone;
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj==null){
            throw new GlobleBusinessException("验证码已过期,请重新获取");
        }
        CodeDto value=(CodeDto)obj;
        if (!value.getCode().equals(smsCode)){
            throw new GlobleBusinessException("验证码错误");
        }

        //4.保存到t_user,t_user_base_info,t_user_account,t_login

        //先添加t_login
        Login login = new Login();
        login.setUsername(phone);
        login.setPassword(password);
        login.setType(1);
        login.setEnabled( true);
        login.setAccountNonExpired( true);
        login.setCredentialsNonExpired( true);
        login.setAccountNonLocked( true);
        JSONResult jsonResult = loginFeignAPI.register(login);
        Object data = jsonResult.getData();
        String jsonString = JSONObject.toJSONString(data);
        Login loginFromDB = JSONObject.parseObject(jsonString, Login.class);
        Long loginId = loginFromDB.getId();

        //t_user
        User user = new User();
        long now = new Date().getTime();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setPhone(phone);
        user.setNickName( phone);
        user.setBitState(0L);
        user.setSecLevel(0);
        insert(user);

        //t_user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setId(user.getId());
        userAccount.setUsableAmount(new BigDecimal(0));
        userAccount.setFrozenAmount(new BigDecimal(0));
        userAccount.setCreateTime(now);
        userAccount.setUpdateTime(now);
        userAccountService.insert(userAccount);

        int i=1/0;
        //t_user_base_info
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setId(user.getId());
        userBaseInfo.setCreateTime(now);
        userBaseInfo.setUpdateTime(now);
        userBaseInfo.setRegChannel(regChannel);
        userBaseInfoService.insert(userBaseInfo);

        redisTemplate.delete(key);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
