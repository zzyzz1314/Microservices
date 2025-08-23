package cn.zwh.ymcc.domain;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author whale
 * @since 2025-08-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_course_order")
public class CourseOrder extends Model<CourseOrder> {

    private static final long serialVersionUID = 1L;

    //未支付
    public static final Integer PAY_NO = 0;
    //已支付
    public static final Integer PAY_SUCCESS = 1;
    //订单取消
    public static final Integer ORDER_CANCEL = 2;
    //支付失败
    public static final Integer PAY_FAIL = 3;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 最后支付更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 支付总的价格
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 秒杀数量
     */
    @TableField("total_count")
    private Integer totalCount;
    /**
     * // 订单状态 ：
     * //0下单成功待支付，
     * //1支付成功订单完成
     * //2用户手动取消订单(未支付)
     * //3.支付失败
     * //4.超时自动订单取消
     */
    @TableField("status_order")
    private Integer statusOrder;
    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 订单标题
     */
    private String title;
    private Integer version;
    /**
     * 支付方式:0余额直接，1支付宝，2微信,3银联
     */

    /*
     * 订单明细的集合
     * */
    @TableField(exist = false)
    private List<CourseOrderItem> courseOrderItemList = new ArrayList<>();

    @TableField("pay_type")
    private Integer payType;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}