package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDto {

    //课程id
    private List<Long> courseIds;

    //支付类型 1:支付宝
    private Integer payType;

    //防止订单重复提交
    private String token;

    //订单类型是否是秒杀 0:不是 1:是
    private Integer type;
}
