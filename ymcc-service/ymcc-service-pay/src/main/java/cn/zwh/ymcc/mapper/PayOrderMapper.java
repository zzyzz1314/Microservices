package cn.zwh.ymcc.mapper;

import cn.zwh.ymcc.domain.PayOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whale
 * @since 2025-08-23
 */
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    /*
    * 根据订单，修改支付状态
    * */
    void updateStatusByOrderNo(@Param("outTradeNo") String outTradeNo, @Param("now") Date now, @Param("payStatus") Integer payStatus);
}
