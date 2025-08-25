package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.api.CourseFeignAPI;
import cn.zwh.ymcc.constants.BusinessConstants;
import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.domain.CourseOrderItem;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.dto.CourseDto;
import cn.zwh.ymcc.dto.CourseInfoDto;
import cn.zwh.ymcc.dto.PlaceOrderDto;
import cn.zwh.ymcc.dto.UpdateOrderStatusDto;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.mapper.CourseOrderMapper;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.ICourseOrderItemService;
import cn.zwh.ymcc.service.ICourseOrderService;
import cn.zwh.ymcc.util.CodeGenerateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-21
 */
@Slf4j
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private CourseFeignAPI courseFeignAPI;

    @Autowired
    private ICourseOrderItemService courseOrderItemService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private CourseOrderMapper courseOrderMapper;

    @Override
    public String placeOrder(PlaceOrderDto placeOrderDto) {

        List<Long> courseIds = placeOrderDto.getCourseIds();
        Integer payType = placeOrderDto.getPayType();
        String token = placeOrderDto.getToken();
        Integer type = placeOrderDto.getType();

        //1.校验
        if (courseIds == null
                || token == null
                || payType == null
                || type == null
        ) {
            throw new GlobleBusinessException("订单参数错误");
        }

        //2.token校验
        Long loginId = 3l;
        String key = String.format(BusinessConstants.REDIS_PREVENT_REPEAT_SUBMIT_ORDER, loginId, courseIds.get(0));
        Object redisToken = redisTemplate.opsForValue().get(key);
        if (redisToken == null ||!token.equals(redisToken.toString())) {
            throw new GlobleBusinessException("请勿重复提交订单");
        }

        //3.生成订单和订单明细表 远程调用courseInfo
        String join = StringUtils.join(courseIds, ",");
        JSONResult jsonResult = courseFeignAPI.info(join);
        Object data = jsonResult.getData();
        String jsonString = JSONObject.toJSONString(data);
        CourseInfoDto courseInfoDto = JSONObject.parseObject(jsonString, CourseInfoDto.class);
        BigDecimal totalAmount = courseInfoDto.getTotalAmount();
        //所有下单课程
        List<CourseDto> courseInfos = courseInfoDto.getCourseInfos();

        //创建一个订单 t_course_order
        CourseOrder courseOrder = new CourseOrder();
        Date date = new Date();
        courseOrder.setCreateTime(date);
        courseOrder.setUpdateTime(date);
        String orderNo = CodeGenerateUtils.generateOrderSn(loginId);
        courseOrder.setOrderNo(orderNo);
        courseOrder.setTotalAmount(totalAmount);
        courseOrder.setStatusOrder(CourseOrder.PAY_NO);
        courseOrder.setTitle(courseInfos.get(0).getCourse().getName());
        courseOrder.setPayType(payType);
        courseOrder.setTotalCount(courseInfos.size());
        courseOrder.setUserId(loginId);
        //insert(courseOrder);

        //创建一个订单详细表 t_course_order_item
        List<CourseOrderItem> courseOrderItems = new ArrayList<>();
        for (CourseDto courseInfo : courseInfos) {
            CourseOrderItem courseOrderItem = new CourseOrderItem();
            courseOrderItem.setCreateTime(date);
            courseOrderItem.setUpdateTime(date);
            courseOrderItem.setCourseId(courseInfo.getCourse().getId());
            courseOrderItem.setAmount(courseInfo.getCourseMarket().getPrice());
            courseOrderItem.setCourseName(courseInfo.getCourse().getName());
            courseOrderItem.setCoursePic(courseInfo.getCourse().getPic());
            courseOrderItem.setOrderNo(orderNo);
            courseOrderItem.setCount(1);
            courseOrderItems.add(courseOrderItem);
        }
        courseOrder.setCourseOrderItemList(courseOrderItems);
        //courseOrderItemService.insertBatch(courseOrderItems);

        PayOrder payOrder = new PayOrder();
        payOrder.setCreateTime(date);
        payOrder.setAmount(totalAmount);
        payOrder.setPayType(payType);
        payOrder.setUserId(loginId);
        payOrder.setOrderNo(orderNo);
        payOrder.setSubject(courseInfos.get(0).getCourse().getName());
        payOrder.setPayStatus(PayOrder.PAY_NO);

        //发送消息
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(
                //事务监听器组名字
                BusinessConstants.MQ_COURSEORDER_PAY_GROUP_TRANSACTION,
                //主题：标签
                BusinessConstants.MQ_TOPIC_ORDER + ":" + BusinessConstants.MQ_TAGS_COURSEORDER_PAYORDER,
                //消息：用作保存支付单
                MessageBuilder.withPayload(JSON.toJSONString(payOrder)).build(),
                //参数：用作保存课程订单和明细
                courseOrder);

        if (transactionSendResult.getSendStatus()!= SendStatus.SEND_OK){
            throw new GlobleBusinessException("订单创建失败");
        }

        // 发送延迟消息，为了处理超时未处理文件
        SendResult sendResult = rocketMQTemplate.syncSend(BusinessConstants.ROCKETMQ_TOPIC_ORDER_LEAVE_TIMEOUT + ":" + BusinessConstants.ROCKETMQ_TAGS_ORDER_LEAVE_TIMEOUT,
                MessageBuilder.withPayload(orderNo).build(),
                3000,
                4);
        log.info("发送延迟消息结果为:{}",sendResult);
        SendStatus sendStatus = sendResult.getSendStatus();
        if (sendStatus != SendStatus.SEND_OK){
            log.error("发送延迟消息失败:{}",sendResult);
            //存表， 人工干涉
        }


        redisTemplate.delete(key);
        return orderNo;
    }

    @Override
    public void saveOrderAndItem(CourseOrder courseOrder) {

        //根据orderNo查询
        Wrapper<CourseOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no", courseOrder.getOrderNo());
        CourseOrder courseOrderFromDB = selectOne(wrapper);
        if(courseOrderFromDB != null){
            throw new GlobleBusinessException("订单重复提交");
        }
        insert(courseOrder);
        courseOrderItemService.insertBatch(courseOrder.getCourseOrderItemList());
    }

    @Override
    public JSONResult updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto) {
        courseOrderMapper.updateOrderStatus(updateOrderStatusDto);
        return JSONResult.success();
    }
}
