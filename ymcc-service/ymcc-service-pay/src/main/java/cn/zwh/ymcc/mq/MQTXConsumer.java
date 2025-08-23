package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.service.IPayOrderService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
@RocketMQMessageListener(
        topic = BusinessConstants.MQ_TOPIC_ORDER,
        selectorExpression = BusinessConstants.MQ_TAGS_COURSEORDER_PAYORDER,
        consumerGroup = "service-pay-consumer"
)
@Component
@Slf4j
public class MQTXConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private IPayOrderService payOrderService;
    @Override
    public void onMessage(MessageExt messageExt) {
        if (messageExt!=null&&messageExt.getBody()!=null&&messageExt.getBody().length>0){
            String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);

            //转换成PayOrder对象
            PayOrder payOrder = JSONObject.parseObject(s, PayOrder.class);

            EntityWrapper<PayOrder> wrapper = new EntityWrapper<>();
            wrapper.eq("order_no", payOrder.getOrderNo());
            PayOrder payOrderDB = payOrderService.selectOne(wrapper);
            if (payOrderDB==null){
                payOrderService.insert(payOrder);
                log.info("消费成功======================");
            }
        }
    }
}
