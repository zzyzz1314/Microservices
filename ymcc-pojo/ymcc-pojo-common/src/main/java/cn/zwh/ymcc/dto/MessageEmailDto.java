package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEmailDto {

    private String title;
    private String content;
    private String copyto;
    private Date sendTime;

    private List<UserEmailDto> userEmailDtos;
}
