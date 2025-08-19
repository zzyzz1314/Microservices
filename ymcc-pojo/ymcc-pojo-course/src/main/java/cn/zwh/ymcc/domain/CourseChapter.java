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
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频
 * </p>
 *
 * @author whale
 * @since 2025-08-14
 */
@TableName("t_course_chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseChapter extends Model<CourseChapter> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 章节名称
     */
    private String name;
    /**
     * 第几章
     */
    private Integer number;
    /**
     * 课程ID
     */
    @TableField("course_id")
    private Long courseId;
    /**
     * 课程名
     */
    @TableField("course_name")
    private String courseName;

    //一个章节中有多个视频
    @TableField(exist = false)
    private List<MediaFile> mediaFiles=new ArrayList<>();

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
