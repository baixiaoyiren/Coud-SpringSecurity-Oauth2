package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:16
 * @Description: 主题模式和路由模式差不多
 */
@Configuration
public class MqConfig {

    @Bean(value = Constant.TOPIC_QUEUE)
    public Queue topicQueue(){
        return new Queue(Constant.TOPIC_QUEUE,true,false,false);
    }

    @Bean(value = Constant.TOPIC_EXCANGE)
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(Constant.TOPIC_EXCANGE).durable(true).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding topicBinding(){
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(Constant.TOPIC_ROUTING_KEY).noargs();
    }


    /**
     * 路由模式
     *
     * @return
     */
    @Bean
    public Queue routeQueue1(){
        return new Queue(Constant.ROUTE_QUEUE_1);
    }

    /**
     * 路由模式
     *
     * @return
     */
    @Bean
    public Queue routeQueue2(){
        return new Queue(Constant.ROUTE_QUEUE_2);
    }

    /**
     * 路由交换机
     */
    @Bean
    public DirectExchange routeExchange(){
        return ExchangeBuilder.directExchange(Constant.ROUTE_EXCHANGE).durable(true).build();
    }


    // 绑定交换机和队列
    @Bean
    public Binding routeBinding1(){
        return BindingBuilder.bind(routeQueue1()).to(routeExchange()).with(Constant.ROUTE_KEY);
    }



    // 绑定交换机和队列
    @Bean
    public Binding routeBinding2(){
        return BindingBuilder.bind(routeQueue2()).to(routeExchange()).with(Constant.ROUTE_KEY);
    }

}
