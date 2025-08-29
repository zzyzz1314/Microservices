package cn.zwh.ymcc.domain;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2025-08-29
 */
@TableName("t_kill_activity")
public class KillActivity extends Model<KillActivity> {

    private static final long serialVersionUID = 1L;

    //待发布
    public static final Integer PUBLISH_STATUS_WAIT = 0;
    //已发布
    public static final Integer PUBLISH_STATUS_SUCCESS = 1;
    //已取消
    public static final Integer PUBLISH_STATUS_CANCEL = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField("time_str")
    private String timeStr;
    @TableField("begin_time")
    private Long beginTime;
    @TableField("end_time")
    private Long endTime;
    /**
     * 0待发布，1已发布 ，2已取消
     */
    @TableField("publish_status")
    private Integer publishStatus;
    @TableField("publish_time")
    private Date publishTime;
    @TableField("create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KillActivity{" +
        ", id=" + id +
        ", name=" + name +
        ", timeStr=" + timeStr +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", publishStatus=" + publishStatus +
        ", publishTime=" + publishTime +
        ", createTime=" + createTime +
        "}";
    }
}
