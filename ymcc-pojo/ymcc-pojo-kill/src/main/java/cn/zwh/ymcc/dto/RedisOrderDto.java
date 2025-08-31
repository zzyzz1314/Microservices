package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisOrderDto {
    //临时订单号
    private String orderNo;

    //金额
    private BigDecimal totalPrice;

    //用户ID
    private Long loginId;

    private String courseName;

    private Long courseId;

    private String coursePic;
}
