package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.service.IPayOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static cn.zwh.ymcc.constants.BusinessConstants.*;

@RocketMQMessageListener(
        topic = ROCKETMQ_TOPIC_ORDER_LEAVE_TIMEOUT,
        selectorExpression = ROCKETMQ_TAGS_ORDER_LEAVE_TIMEOUT,
        consumerGroup = "service-order-consumer-delay",
        messageModel = MessageModel.BROADCASTING // 广播模式
)
@Component
@Slf4j
public class MQConsumerOrderDelay implements RocketMQListener<MessageExt> {
    @Autowired
    private IPayOrderService payOrderService;

    @Override
    public void onMessage(MessageExt messageExt) {

        String orderNo = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        Wrapper<PayOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no", orderNo);
        PayOrder payOrder = payOrderService.selectOne(wrapper);
        if (payOrder.getPayStatus()==0){
            //修改订单状态， 2
            payOrder.setPayStatus(CourseOrder.ORDER_CANCEL);
            payOrderService.updateById(payOrder);

            // 通知支付宝，该订单已超时未支付，请关单
            payOrderService.cancelOrder(orderNo);

            log.info("订单超时，订单号：{},订单状态：{}", orderNo, CourseOrder.ORDER_CANCEL);
        }else {

            log.info("订单已支付，订单号：{},订单状态：{}", orderNo, CourseOrder.PAY_SUCCESS);
        }

    }
}
