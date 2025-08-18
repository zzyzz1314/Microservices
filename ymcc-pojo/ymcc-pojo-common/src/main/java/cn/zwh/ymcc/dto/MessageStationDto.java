package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageStationDto {
    private String title;
    private  String content;
    private  String type;
    private Date sendTime;
    private Integer isread;
    private List<Long> userIds;
}
