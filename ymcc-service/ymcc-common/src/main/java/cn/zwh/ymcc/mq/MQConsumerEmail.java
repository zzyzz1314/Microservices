package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.MessageEmail;
import cn.zwh.ymcc.domain.MessageSms;
import cn.zwh.ymcc.dto.MessageEmailDto;
import cn.zwh.ymcc.dto.MessageSMSDto;
import cn.zwh.ymcc.dto.UserEmailDto;
import cn.zwh.ymcc.dto.UserPhoneIpDto;
import cn.zwh.ymcc.service.IMessageEmailService;
import cn.zwh.ymcc.service.IMessageSmsService;
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
 * 消费邮件推送消息
 */
@RocketMQMessageListener(
        topic = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC,
        selectorExpression = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_EMAIL,
        consumerGroup = "service-common-consumer-email"
)
@Component
@Slf4j
public class MQConsumerEmail implements RocketMQListener<MessageExt> {

    @Autowired
    private IMessageEmailService messageEmailService;

    @Override
    public void onMessage(MessageExt messageExt) {
        //1.获取消息
        String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        MessageEmailDto messageEmailDto = JSONObject.parseObject(s, MessageEmailDto.class);
        //存到数据库中
        List<UserEmailDto> userEmailDtos = messageEmailDto.getUserEmailDtos();
        List<MessageEmail> messageEmails = new ArrayList<>();
        userEmailDtos.forEach(userEmailDto -> {
            MessageEmail messageEmail = new MessageEmail();
            BeanUtils.copyProperties(messageEmailDto, messageEmail);
            messageEmail.setSendTime(new Date());
            messageEmail.setUserId(userEmailDto.getUserId());
            messageEmail.setEmail(userEmailDto.getEmail());
            messageEmails.add(messageEmail);
        });

        messageEmailService.insertBatch(messageEmails);

        log.info("消息消费:邮件推送成功");
    }
}
