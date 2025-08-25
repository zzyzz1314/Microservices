package cn.zwh.ymcc.constants;

public class BusinessConstants {

    //redis================================================================
    //手机号注册
    public static final String PHONE_REGISTER="phone_register";
    //课程类型树形数据
    public static final String REDIS_COURSE_TYPE_DATA="redis_course_type_data";

    //防止重复提交订单的键
    public static final String REDIS_PREVENT_REPEAT_SUBMIT_ORDER="prevent_repeat_submit_order:%s:%s";

    //订单服务，发送事务消息，监听器的组名
    public static final String MQ_COURSEORDER_PAY_GROUP_TRANSACTION="mq_courseorder_pay_group_transaction";
    //订单服务，发送事务消息，监听器的 topic
    public static final String MQ_TOPIC_ORDER="mq_topic_order";
    //订单服务，发送事务消息，监听器的 tags
    public static final String MQ_TAGS_COURSEORDER_PAYORDER="mq_tags_courseorder_payorder";

    // 超时未支付  延迟消息的topic
    public static final String ROCKETMQ_TOPIC_ORDER_LEAVE_TIMEOUT="rocketmq_topic_order_leave_timeout";
    // 超时未支付  延迟消息的tags
    public static final String ROCKETMQ_TAGS_ORDER_LEAVE_TIMEOUT="rocketmq_tags_order_leave_timeout";

    //rocketMQ==============================================================

    //媒体推送消息 topic
    public static final String ROCKETMQ_TOPIC_MEDIA="rocketmq_topic_media";

    //媒体推送消息 tags
    public static final String ROCKETMQ_TAGS_MEDIA="rocketmq_tags_media";

    //消费者组 group
    public static final String ROCKETMQ_CONSUMER_GROUP_MEDIA="rocketmq_consumer_group_media";

    //推送消息的 topic
    public static final String ROCKETMQ_COURSE_PUBLISH_MESSAGE_TOPIC="publish_message_topic";

    //站内 tags
    public static final String ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_STATION="publish_message_tags_station";

    //短信的 tags
    public static final String ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_SMS="publish_message_tags_sms";

    //邮件的 tags
    public static final String ROCKETMQ_COURSE_PUBLISH_MESSAGE_TAGS_EMAIL="publish_message_tags_email";

    //rocketMQ==============================================================
}
