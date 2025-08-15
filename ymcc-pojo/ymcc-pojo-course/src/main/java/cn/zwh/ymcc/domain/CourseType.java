package cn.zwh.ymcc.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程目录
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_course_type")
public class CourseType extends Model<CourseType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Long createTime;
    @TableField("update_time")
    private Long updateTime;
    /**
     * 类型名
     */
    private String name;
    /**
     * 父ID
     */
    private Long pid;
    /**
     * 图标
     */
    private String logo;
    /**
     * 描述
     */
    private String description;
    @TableField("sort_index")
    private Integer sortIndex;
    /**
     * 路径
     */
    private String path;
    /**
     * 课程数量
     */
    @TableField("total_count")
    private Integer totalCount;
    @TableField(exist = false)
    private List<CourseType> children =new ArrayList<>();




    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
