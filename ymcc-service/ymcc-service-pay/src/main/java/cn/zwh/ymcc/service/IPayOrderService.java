package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.dto.AlipayNotifyDto;
import cn.zwh.ymcc.dto.ApplyDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-23
 */
public interface IPayOrderService extends IService<PayOrder> {

    /*
    * 根据订单编号，查询支付单是否存在
    * */
    PayOrder checkPayOrder(String orderNo);

    /*
    * 支付
    * */
    String apply(ApplyDto applyDto);

    /*
    * 支付宝异步回调
    * */
    String asyncNotify(AlipayNotifyDto alipayNotifyDto);

    /*
    * 关单操作
    * */
    void cancelOrder(String orderNo);

    /*
    * 根据订单号，查询订单状态
    * */
    String queryOrderStatus(String orderNo);
}
