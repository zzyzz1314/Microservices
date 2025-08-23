package cn.zwh.ymcc.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whale
 * @since 2025-08-21
 */
@TableName("t_course_order_item")
public class CourseOrderItem extends Model<CourseOrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 对应秒杀订单
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 秒杀课程的价格
     */
    private BigDecimal amount;
    /**
     * 秒杀到课程的数量
     */
    private Integer count;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 秒杀课程ID，不是课程ID
     */
    @TableField("course_id")
    private Long courseId;
    /**
     * 秒杀课程名字
     */
    @TableField("course_name")
    private String courseName;
    /**
     * 封面
     */
    @TableField("course_pic")
    private String coursePic;
    private Integer version;
    @TableField("order_no")
    private String orderNo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CourseOrderItem{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", amount=" + amount +
        ", count=" + count +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", courseId=" + courseId +
        ", courseName=" + courseName +
        ", coursePic=" + coursePic +
        ", version=" + version +
        ", orderNo=" + orderNo +
        "}";
    }
}
