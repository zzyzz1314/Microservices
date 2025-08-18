package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.MessageSms;
import cn.zwh.ymcc.domain.MessageStation;
import cn.zwh.ymcc.dto.MessageSMSDto;
import cn.zwh.ymcc.dto.MessageStationDto;
import cn.zwh.ymcc.dto.UserPhoneIpDto;
import cn.zwh.ymcc.service.IMessageSmsService;
import cn.zwh.ymcc.service.IMessageStationService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消费站内推送消息
 */
@RocketMQMessageListener(
        topic = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC,
        selectorExpression = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_SMS,
        consumerGroup = "service-common-consumer-sms"
)
@Component
@Slf4j
public class MQConsumerSMS implements RocketMQListener<MessageExt> {

    @Autowired
    private IMessageSmsService messageSmsService;

    @Override
    public void onMessage(MessageExt messageExt) {
        //1.获取消息
        String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        MessageSMSDto messageSMSDto = JSONObject.parseObject(s, MessageSMSDto.class);
        //存到数据库中
        List<UserPhoneIpDto> userPhoneIpDtos = messageSMSDto.getUserPhoneIpDtos();
        List<MessageSms> messageSmsList = new ArrayList<>();
        userPhoneIpDtos.forEach(userPhoneIpDto -> {
            MessageSms messageSms = new MessageSms();
            BeanUtils.copyProperties(messageSMSDto, messageSms);
            messageSms.setIp(userPhoneIpDto.getIp());
            messageSms.setPhone(userPhoneIpDto.getPhone());
            messageSms.setUserId(userPhoneIpDto.getUserId());
            messageSms.setSendTime(new Date());
            messageSmsList.add(messageSms);
        });

        messageSmsService.insertBatch(messageSmsList);

        log.info("消息消费:短信推送成功");
    }
}
