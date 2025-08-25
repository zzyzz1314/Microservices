package cn.zwh.ymcc;

import cn.zwh.ymcc.dto.UpdateOrderStatusDto;
import cn.zwh.ymcc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface OrderFeignAPI {

    @PostMapping("/courseOrder/updateOrderStatus")
    JSONResult updateOrderStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto);
}
