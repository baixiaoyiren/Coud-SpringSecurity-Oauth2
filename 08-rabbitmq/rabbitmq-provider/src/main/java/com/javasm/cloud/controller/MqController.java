package com.javasm.cloud.controller;

import com.javasm.cloud.entity.Constant;
import com.javasm.cloud.entity.Response;
import com.javasm.cloud.feign.UserServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:26
 * @Description:
 */
@RestController
@AllArgsConstructor
public class MqController{

    private RabbitTemplate rabbitTemplate;

    private UserServiceFeign userServiceFeign;

    /**
     * 主题模式发送消息
     *
     * @param userName
     * @return
     */
    @GetMapping("/topic/userInfo/{userName}")
    public Response getUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        rabbitTemplate.convertAndSend(Constant.TOPIC_EXCANGE,Constant.MY_TOPIC_ROUTING_KEY,userInfo);
        return new Response().code(HttpStatus.OK).msg("ok");
    }


    /**
     * 广播模式发送消息
     * @param userName
     * @return
     */

    @GetMapping("/fanout/userInfo/{userName}")
    public Response sendUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        rabbitTemplate.convertAndSend(Constant.FANOUT_EXCANGE,Constant.SEND_MESSAGE_KEY,userInfo);
        return new Response().code(HttpStatus.OK).msg("ok");
    }


    /**
     * 简单模式发送消息
     * @param userName
     * @return
     */
    @GetMapping("/simple/userInfo/{userName}")
    public Response simpleUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        rabbitTemplate.convertAndSend(Constant.SIMPLE_MODEL,userInfo);
        return new Response().code(HttpStatus.OK).msg("ok");
    }


    /**
     * 工作模式发送消息
     */
    @GetMapping("/work/userInfo/{userName}")
    public Response workUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        rabbitTemplate.convertAndSend(Constant.WORK_QUEUE,userInfo);
        return new Response().code(HttpStatus.OK).msg("ok");
    }


    /**
     * 路由模式发送消息
     */
    @GetMapping("/route/userInfo/{userName}")
    public Response routeUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        rabbitTemplate.convertAndSend(Constant.ROUTE_EXCHANGE,Constant.ROUTE_KEY,userInfo);
        return new Response().code(HttpStatus.OK).msg("ok");
    }


    /**
     * 路由模式发送消息
     */
    @GetMapping("/route/userInfo/{userName}/{routingKey}")
    public Response batchRouteUserInfo(@PathVariable String userName,@PathVariable String routingKey) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        for (int i = 0; i < 10; i++) {
            //rabbitTemplate.convertAndSend(Constant.BATCH_ROUTE_EXCHANGE,routingKey,userInfo);
            rabbitTemplate.convertAndSend(Constant.BATCH_ROUTE_EXCHANGE,routingKey,userInfo);
        }
        return new Response().code(HttpStatus.OK).msg("ok").data(userInfo.getData());
    }


    /**
     * direct模式发送消息
     */
    @GetMapping("/route/userInformation/{userName}")
    public Response directRouteUserInfo(@PathVariable String userName) {
        Response userInfo = userServiceFeign.getUserInfo(userName);
        for (int i = 0; i < 10; i++) {
            //rabbitTemplate.convertAndSend(Constant.BATCH_ROUTE_EXCHANGE,routingKey,userInfo);
            rabbitTemplate.convertAndSend(Constant.NORMAL_EXCHANGE,Constant.NORMAL_KEY,userInfo,correlationData->{
                correlationData.getMessageProperties().setExpiration("6000");
                return correlationData;
            });
        }
        return new Response().code(HttpStatus.OK).msg("ok").data(userInfo.getData());
    }



}
