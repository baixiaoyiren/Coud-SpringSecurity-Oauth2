package com.javasm.cloud.mq.listener;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasm.cloud.entity.Constant;
import com.javasm.cloud.entity.Response;
import com.javasm.cloud.entity.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-13:07
 * @Description:
 */
@Slf4j
@Component
public class RabbitMQListener {

    /**
     * 主题模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.TOPIC_QUEUE})
    public void receviceConfirmMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接收到的队列confirm队列的消息：{}",msg);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 广播模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.FANOUT_QUEUE_EMAIL,Constant.FANOUT_QUEUE_MSG})
    public void receviceFanoutMessage(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody());
        log.info("接收到的队列fanout队列的消息：{},当前队列为:{}",msg,consumerQueue);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 简单模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.SIMPLE_MODEL})
    public void receviceSimpleMessage(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody());
        log.info("接收到的队列simple队列的消息：{},当前队列为:{}",msg,consumerQueue);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 简单模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.WORK_QUEUE})
    public void receviceWorkMessage1(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody());
        log.info("接收到的队列Work队列的消息12：{},当前队列为:{}",msg,consumerQueue);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 简单模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.WORK_QUEUE})
    public void receviceWorkMessage2(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody());
        log.info("接收到的队列Work队列的消息11：{},当前队列为:{}",msg,consumerQueue);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 路由模式的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.ROUTE_QUEUE_1,Constant.ROUTE_QUEUE_2})
    public void receviceRouteMessage1(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody());
        log.info("接收到的队列route队列的消息：{},当前队列为:{}",msg,consumerQueue);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
            String userStr = JSON.toJSONString(response.getData());
            User user = JSON.parseObject(userStr, User.class);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    ///**
    // * direct模式的队列监听
    // *
    // * @param message
    // */
    //@RabbitListener(queues = {Constant.NORMAL_QUEUE})
    //public void normal(Message message, Channel channel){
    //   if (message.getMessageProperties().getDeliveryTag() != 1){
    //       try {
    //           channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,false);
    //       } catch (IOException e) {
    //           e.printStackTrace();
    //       }
    //   }else {
    //       String consumerQueue = message.getMessageProperties().getConsumerQueue();
    //       String msg = new String(message.getBody());
    //       log.info("接收到的队列direct队列的消息：{},当前队列为:{}",msg,consumerQueue);
    //       ObjectMapper mapper = new ObjectMapper();
    //       try {
    //           Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
    //           String userStr = JSON.toJSONString(response.getData());
    //           User user = JSON.parseObject(userStr, User.class);
    //           System.out.println(user);
    //       } catch (IOException e) {
    //           e.printStackTrace();
    //       }
    //       try {
    //           channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    //       } catch (IOException e) {
    //           e.printStackTrace();
    //       }
    //   }
    //}


    /**
     * 死信的队列监听
     *
     * @param message
     */
    @RabbitListener(queues = {Constant.DEAD_QUEUE})
    public void dead(Message message,Channel channel){
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicAck(deliveryTag,true);
            String consumerQueue = message.getMessageProperties().getConsumerQueue();
            String msg = new String(message.getBody());
            log.info("接收到的队列direct队列异常的消息：{},当前队列为:{}",msg,consumerQueue);
            ObjectMapper mapper = new ObjectMapper();
            try {
                Response response = mapper.readValue(msg.getBytes(StandardCharsets.UTF_8), Response.class);
                String userStr = JSON.toJSONString(response.getData());
                User user = JSON.parseObject(userStr, User.class);
                System.out.println(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            try {
                channel.basicReject(deliveryTag,true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
