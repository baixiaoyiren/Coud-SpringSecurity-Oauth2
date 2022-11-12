package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-23-17:00
 * Description: 死信队列
 */

@Configuration
public class DeadQueueConfig {
    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue(){
        return new Queue(Constant.DEAD_QUEUE);
    }



    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange deadExchange(){
        return ExchangeBuilder.directExchange(Constant.DEAD_EXCHANGE).durable(true).build();
    }

    /**
     * 普通队列
     */
    @Bean
    public Queue normalQueue(){
        //正常队列设置死信交换机
        Queue queue = new Queue(Constant.NORMAL_QUEUE);
        queue.addArgument("x-dead-letter-exchange",Constant.DEAD_EXCHANGE);
        queue.addArgument("x-dead-letter-routing-key",Constant.DEAD_KEY);
        return queue;
    }


    /**
     * 普通交换机
     */
    @Bean
    public DirectExchange normalExchange(){
        return ExchangeBuilder.directExchange(Constant.NORMAL_EXCHANGE).durable(true).build();
    }

    /**
     * 绑定死信队列
     */
    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(Constant.DEAD_KEY);
    }

    /**
     * 绑定普通交换机
     */
    @Bean
    public Binding normalBinding(){
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(Constant.NORMAL_KEY);
    }

}
