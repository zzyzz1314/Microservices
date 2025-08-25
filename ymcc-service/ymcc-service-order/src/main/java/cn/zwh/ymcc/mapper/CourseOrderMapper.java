package cn.zwh.ymcc.mapper;

import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.dto.UpdateOrderStatusDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whale
 * @since 2025-08-21
 */
public interface CourseOrderMapper extends BaseMapper<CourseOrder> {

    /*
    * 根据订单编号修改订单状态
    * */
    void updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto);
}
