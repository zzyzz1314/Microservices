package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.MediaFile;
import cn.zwh.ymcc.service.IMediaSenderService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MediaSenderServiceImpl implements IMediaSenderService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public boolean send(MediaFile mediaFile) {
        if (mediaFile==null){
            throw new RuntimeException("文件内容为null，不能上传");
        }
        SendResult sendResult = rocketMQTemplate.syncSend(BusinessConstants.ROCKETMQ_TOPIC_MEDIA +":"+
                BusinessConstants.ROCKETMQ_TAGS_MEDIA, JSONObject.toJSONString(mediaFile));
        log.info("发送结果：{}",sendResult);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }
}
