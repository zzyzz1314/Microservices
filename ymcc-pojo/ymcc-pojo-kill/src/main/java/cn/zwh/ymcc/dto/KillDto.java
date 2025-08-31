package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KillDto {

    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @NotNull(message = "课程ID不能为空")
    private Long courseId;
}
