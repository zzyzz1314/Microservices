package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.PayOrder;
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

    PayOrder checkPayOrder(String orderNo);

    String apply(ApplyDto applyDto);
}
