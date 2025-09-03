package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KillPlaceOrderDto {

    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @NotNull(message = "支付类型不能为空")
    private Integer payType;

    @NotNull(message = "token不能为空")
    private String token;
}
