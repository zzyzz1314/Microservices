package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.dto.AlipayNotifyDto;
import cn.zwh.ymcc.dto.ApplyDto;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.IPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IPayOrderService payOrderService;

    @GetMapping("/checkPayOrder/{orderNo}")
    public JSONResult checkPayOrder(@PathVariable("orderNo") String orderNo) {
        PayOrder payOrder =payOrderService.checkPayOrder(orderNo);
        if (payOrder==null){
            return JSONResult.error("未找到该订单");
        }
        return JSONResult.success(payOrder);
    }


    /*
    * 支付
    * */
    @PostMapping("/apply")
    public JSONResult apply(@RequestBody ApplyDto applyDto) {
        String from = payOrderService.apply(applyDto);
        return JSONResult.success(from);
    }

    /*
    * 异步回调函数
    * */
    @PostMapping("/notifyPay")
    public String asyncNotify(AlipayNotifyDto alipayNotifyDto) {
        try {
            log.info("异步通知已接收到=========================");
            return payOrderService.asyncNotify(alipayNotifyDto);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*
    * 根据订单号查询订单状态，支付宝的订单状态
    * */
    @GetMapping("/queryOrderStatus/{orderNo}")
    public JSONResult queryOrderStatus(@PathVariable("orderNo") String orderNo) {
        String resp = payOrderService.queryOrderStatus(orderNo);
        return JSONResult.success(resp);
    }


}
