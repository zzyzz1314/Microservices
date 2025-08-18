package cn.zwh.ymcc;

import cn.zwh.ymcc.domain.User;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("service-user")
public interface PublishFeignAPI {
    @PostMapping("/user/userIds")
    public List<User> findAll();
}
