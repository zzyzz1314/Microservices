package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.dto.PlaceOrderDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-21
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    /**
     * 创建订单和订单明细
     * @param placeOrderDto
     * @return
     */
    String placeOrder(PlaceOrderDto placeOrderDto);

    /**
     * 保存订单和订单明细
     * @param courseOrder
     */
    void saveOrderAndItem(CourseOrder courseOrder);
}
