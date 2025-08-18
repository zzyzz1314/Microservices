package cn.zwh.ymcc.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whale
 * @since 2025-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_message_station")
public class MessageStation extends Model<MessageStation> {

    private static final long serialVersionUID = 1L;

    //已读
    public static final Integer READ_YES=1;
    //未读
    public static final Integer READ_NO=0;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    /**
     * 类型
     */
    private String type;
    @TableField("send_time")
    private Date sendTime;
    private Integer isread;
    @TableField("user_id")
    private Long userId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageStation{" +
        ", id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", type=" + type +
        ", sendTime=" + sendTime +
        ", isread=" + isread +
        ", userId=" + userId +
        "}";
    }
}
