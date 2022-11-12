package com.javasm.cloud.mq.listener.batch;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasm.cloud.entity.Constant;
import com.javasm.cloud.entity.Response;
import com.javasm.cloud.entity.User;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-14-00:17
 * @Description:
 */
@Component
@Slf4j
@AllArgsConstructor
public class batchDirectConsumerConfig1 {

    private RedisTemplate<String, Object> redisTemplate;


    @RabbitListener(queues = {Constant.BATCH_ROUTE_QUEUE_1, Constant.BATCH_ROUTE_QUEUE_2, Constant.BATCH_ROUTE_QUEUE_3})
    public void recevieMsg(Message message, Channel channel) throws InterruptedException {
        MessageProperties messageProperties = message.getMessageProperties();


        // long deliveryTag 消息接收tag boolean multiple 是否批量确认
        //System.out.println("deliveryTag="+messageProperties.getDeliveryTag());
        long deliveTag = messageProperties.getDeliveryTag();
        // 消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        //log.error("*************************queue:{}",messageProperties.getConsumerQueue());
        //现在改为设置成手动ack应答（channel.basicAck方法），
        //这样做的目的是保证消息在正确消费后给回馈，说明我正确消费了。这时队列就可以把这条消息删除了，
        //如果消费端接收了消息，但是没有给返回ack应答，那么这条消息会继续存在unacked状态下，占据队列的空间，
        //等到空间满了，就会出现接下来的消息不能被消费的情况。

        //try {
        //    channel.basicAck(3, true);
        //    ObjectMapper mapper = new ObjectMapper();
        //    Response response = mapper.readValue(message.getBody(), Response.class);
        //    String userStr = JSON.toJSONString(response.getData());
        //    User user = JSON.parseObject(userStr, User.class);
        //    log.error(response.getData().toString());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    throw new RuntimeException(e);
        //}


        /**
         * 无异常就确认消息
         * basicAck(long deliveryTag, boolean multiple)
         * deliveryTag:取出来当前消息在队列中的的索引;
         * multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
         * deliveryTag为5及其以下的消息;一般设置为false
         */

        if(deliveTag%10==0){
            try {
                channel.basicAck(deliveTag,true);
                ObjectMapper mapper = new ObjectMapper();

                Response response = mapper.readValue(message.getBody(), Response.class);
                String userStr = JSON.toJSONString(response.getData());
                User user = JSON.parseObject(userStr, User.class);
                user.setAge((int) message.getMessageProperties().getDeliveryTag());

                Object key = redisTemplate.opsForValue().get("key");
                if (ObjectUtils.isEmpty(key)){
                    if (ObjectUtils.isEmpty(redisTemplate.opsForValue().get("id"))){
                        redisTemplate.opsForValue().set("id",1,30, TimeUnit.SECONDS);
                    }else {
                        redisTemplate.opsForValue().increment("id",1);
                    }
                    redisTemplate.delete("key");
                    log.info(user.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                /**
                 * 有异常就绝收消息
                 * basicNack(long deliveryTag, boolean multiple, boolean requeue)
                 * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
                 *         false:将消息丢弃
                 */
                try {
                    channel.basicNack(deliveTag,false,false);
                    // long deliveryTag, boolean requeue
                    // channel.basicReject(deliveryTag,true);

                    Thread.sleep(1000);     // 这里只是便于出现死循环时查看

                    /*
                     * 一般实际异常情况下的处理过程：记录出现异常的业务数据，将它单独插入到一个单独的模块，
                     * 然后尝试3次，如果还是处理失败的话，就进行人工介入处理
                     */

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }else {
            try {
                log.error("拒绝消息");
                channel.basicReject(deliveTag,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
