package cn.zwh.ymcc.mq;

import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.Course;
import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.service.ICourseOrderService;
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

@RocketMQMessageListener(
        topic = BusinessConstants.ROCKETMQ_TOPIC_ORDER_LEAVE_TIMEOUT,
        selectorExpression = BusinessConstants.ROCKETMQ_TAGS_ORDER_LEAVE_TIMEOUT,
        consumerGroup = "service-order-consumer-delay",
        messageModel = MessageModel.BROADCASTING
)
@Component
@Slf4j
public class MQConsumerOrderDelay implements RocketMQListener<MessageExt> {
    @Autowired
    private ICourseOrderService courseOrderService;

    @Override
    public void onMessage(MessageExt messageExt) {

        String orderNo = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no", orderNo);
        CourseOrder courseOrder = courseOrderService.selectOne(wrapper);
        if (courseOrder.getStatusOrder()==0){
            //修改订单状态， 2
            courseOrder.setStatusOrder(CourseOrder.ORDER_CANCEL);
            courseOrderService.updateById(courseOrder);

            log.info("订单超时，订单号：{},订单状态：{}", orderNo, CourseOrder.ORDER_CANCEL);
        }else {

            log.info("订单已支付，订单号：{},订单状态：{}", orderNo, CourseOrder.PAY_SUCCESS);
        }

    }
}
