package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.MessageStation;
import cn.zwh.ymcc.dto.MessageStationDto;
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
 *消费站内推送消息
 */
@RocketMQMessageListener(
        topic = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC,
        selectorExpression = BusinessConstants.ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_STATION,
        consumerGroup = "service-common-consumer-station"
)
@Component
@Slf4j
public class MQConsumerStaticon implements RocketMQListener<MessageExt> {

    @Autowired
    private IMessageStationService messageStationService;
    @Override
    public void onMessage(MessageExt messageExt) {
        //1.获取消息
        String s=new String(messageExt.getBody(), StandardCharsets.UTF_8);
        MessageStationDto messageStationDto = JSONObject.parseObject(s, MessageStationDto.class);
        //存到数据库中
        List<Long> userIds = messageStationDto.getUserIds();

        List<MessageStation> messageStations=new ArrayList<>();
        userIds.forEach(userId -> {
            MessageStation messageStation = new MessageStation();
            BeanUtils.copyProperties(messageStationDto,messageStation);
            messageStation.setUserId(userId);
            messageStation.setSendTime(new Date());
            messageStation.setIsread(MessageStation.READ_NO);
            messageStations.add(messageStation);
        });

        //批量添加
        messageStationService.insertBatch(messageStations);

        log.info("消息消费:短信推送成功");
    }
}
