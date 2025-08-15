package cn.zwh.ymcc;

import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//接口服务的名称
@FeignClient("service-uaa")
public interface LoginFeignAPI {

    @RequestMapping(value="/login/register",method= RequestMethod.POST)
    JSONResult register(@RequestBody Login login);
}
