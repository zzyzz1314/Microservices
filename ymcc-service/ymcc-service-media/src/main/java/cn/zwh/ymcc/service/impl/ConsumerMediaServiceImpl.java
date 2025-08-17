package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.MediaFile;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.IMediaFileService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RocketMQMessageListener(topic = BusinessConstants.ROCKETMQ_TOPIC_MEDIA,
        selectorExpression = BusinessConstants.ROCKETMQ_TAGS_MEDIA,
        consumerGroup = BusinessConstants.ROCKETMQ_CONSUMER_GROUP_MEDIA
)
public class ConsumerMediaServiceImpl implements RocketMQListener<MessageExt> {
    @Autowired
    private IMediaFileService mediaFileService;
    @Override
    public void onMessage(MessageExt messageExt) {
        String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        MediaFile mediaFile = JSONObject.parseObject(s, MediaFile.class);
        JSONResult jsonResult = mediaFileService.handleFile2m3u8(mediaFile);
        log.info("消费消息，推流结果：{}", jsonResult);
    }
}
