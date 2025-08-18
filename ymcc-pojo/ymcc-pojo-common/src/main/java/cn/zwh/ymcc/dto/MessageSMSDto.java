package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSMSDto {
    private String title;
    private String content;
    private String sendTime;

    private List<UserPhoneIpDto> userPhoneIpDtos;

}
