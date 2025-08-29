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
 * @since 2025-08-29
 */
@TableName("t_kill_course")
public class KillCourse extends Model<KillCourse> {

    private static final long serialVersionUID = 1L;

    //待发布
    public static final Integer PUBLISH_STATUS_WAIT = 0;
    //已发布
    public static final Integer PUBLISH_STATUS_SUCCESS = 1;
    //已取消
    public static final Integer PUBLISH_STATUS_CANCEL = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 课程名字
     */
    @TableField("course_name")
    private String courseName;
    /**
     * 对应的课程ID
     */
    @TableField("course_id")
    private Long courseId;
    @TableField("kill_price")
    private BigDecimal killPrice;
    /**
     * 库存
     */
    @TableField("kill_count")
    private Integer killCount;
    /**
     * 每个人可以秒杀的数量,默认1
     */
    @TableField("kill_limit")
    private Integer killLimit;
    /**
     * 秒杀课程排序
     */
    @TableField("kill_sort")
    private Integer killSort;
    /**
     * 秒杀状态:0待发布，1秒杀中，2秒杀结束
     */
    @TableField("publish_status")
    private Integer publishStatus;
    @TableField("course_pic")
    private String coursePic;
    /**
     * 秒杀开始时间
     */
    @TableField("start_time")
    private Long startTime;
    /**
     * 秒杀结束时间
     */
    @TableField("end_time")
    private Long endTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 发布到Redis的时间
     */
    @TableField("publish_time")
    private Date publishTime;
    /**
     * 老师，用逗号隔开
     */
    @TableField("teacher_names")
    private String teacherNames;
    /**
     * 下线时间
     */
    @TableField("offline_time")
    private Date offlineTime;
    @TableField("activity_id")
    private Long activityId;
    @TableField("time_str")
    private String timeStr;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(BigDecimal killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getKillCount() {
        return killCount;
    }

    public void setKillCount(Integer killCount) {
        this.killCount = killCount;
    }

    public Integer getKillLimit() {
        return killLimit;
    }

    public void setKillLimit(Integer killLimit) {
        this.killLimit = killLimit;
    }

    public Integer getKillSort() {
        return killSort;
    }

    public void setKillSort(Integer killSort) {
        this.killSort = killSort;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KillCourse{" +
        ", id=" + id +
        ", courseName=" + courseName +
        ", courseId=" + courseId +
        ", killPrice=" + killPrice +
        ", killCount=" + killCount +
        ", killLimit=" + killLimit +
        ", killSort=" + killSort +
        ", publishStatus=" + publishStatus +
        ", coursePic=" + coursePic +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        ", publishTime=" + publishTime +
        ", teacherNames=" + teacherNames +
        ", offlineTime=" + offlineTime +
        ", activityId=" + activityId +
        ", timeStr=" + timeStr +
        "}";
    }
}
