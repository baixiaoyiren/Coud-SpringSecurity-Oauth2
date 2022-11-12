package com.javasm.cloud.entity;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:10
 * @Description:
 */
public interface Constant {
    String TOPIC_QUEUE = "topicQueue";

    String TOPIC_EXCANGE="topicExchange";

    String TOPIC_ROUTING_KEY = "topic.*";

    String MY_TOPIC_ROUTING_KEY="topic.userInfo";

    /**
     * 广播模式1:以短信发送
     */
    String FANOUT_QUEUE_MSG = "msg";

    /**
     * 广播模式2：以邮件形式发送
     */
    String FANOUT_QUEUE_EMAIL = "email";

    String FANOUT_EXCANGE="fanoutExchange";

    String SEND_MESSAGE_KEY = "send_message";

    /**
     * 简单模式
     */
    String SIMPLE_MODEL = "simple_model";

    /**
     *工作模式
     */
    String WORK_QUEUE = "work";

    /**
     * 路由模式
     */
    String ROUTE_QUEUE_1 = "route_queue1";
    String ROUTE_QUEUE_2 = "route_queue2";
    String ROUTE_EXCHANGE = "exchange_route";
    String ROUTE_KEY = "ROUTE_KEY";


    /**
     * 批量消费
     */
    String BATCH_ROUTE_QUEUE_1 = "batch_route_queue_1";

    String BATCH_ROUTE_EXCHANGE = "batch_exchange";

    String BATCH_ROUTE_QUEUE_2 = "batch_route_queue_2";


    String BATCH_ROUTE_QUEUE_3 = "batch_route_queue_3";

    String BATCH_ROUTE_KEY = "route_key_batch";

    //普通交换机的名称
    String NORMAL_EXCHANGE = "normal_direct_exchange";

    //死信交换机的名称
    String DEAD_EXCHANGE = "dead_direct_exchange";

    //普通队列名称
    String NORMAL_QUEUE = "normal_direct_queue";

    //死信队列名称
    String DEAD_QUEUE = "dead_direct_queue";

    String DEAD_KEY = "dead";

    String NORMAL_KEY = "normal";
}
