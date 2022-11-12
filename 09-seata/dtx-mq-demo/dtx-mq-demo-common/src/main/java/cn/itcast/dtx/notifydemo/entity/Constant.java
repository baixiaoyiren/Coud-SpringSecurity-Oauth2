package cn.itcast.dtx.notifydemo.entity;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-11:51
 * Description:
 */
public interface Constant {


    String MESSAGE_QUEUE = "message.queue";

    String MESSAGE_EXCHANGE = "message.exchange";

    String ROUTING_KEY="message.key";

    String UN_STATE="0";
    String CONSUMER_STATE="1";
    String MESSAGE_RESPONSE_QUEUE = "message.response";
    String MESSAGE_RESPONSE_EXCHANGE = "message.response";
    String RESPONSE_ROUTING_KEY = "response.routing.key";
}
