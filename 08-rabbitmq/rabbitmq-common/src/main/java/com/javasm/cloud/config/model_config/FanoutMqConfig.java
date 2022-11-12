package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-15:31
 * @Description: 广播模式 fanout  发布订阅模式
 */
@Configuration
public class FanoutMqConfig {

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue(){
        return new Queue(Constant.FANOUT_QUEUE_EMAIL);
    }

    @Bean
    public Queue fanoutMsgQueue(){
        return new Queue(Constant.FANOUT_QUEUE_MSG);
    }
    /**
     * 声明交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange(Constant.FANOUT_EXCANGE).durable(true).build();
    }

    /**
     * 绑定队列和交换机
     * @return
     */
    @Bean
    public Binding fanoutBindingByMsg(){
        //return BindingBuilder.bind(fanoutMsgQueue()).to(fanoutExchange()).with(Constant.SEND_MESSAGE_KEY).noargs();
        return BindingBuilder.bind(fanoutEmailQueue()).to(fanoutExchange());
    }

    /**
     * 绑定队列和交换机
     * @return
     */
    @Bean
    public Binding fanoutBindingByEmail(){
        return BindingBuilder.bind(fanoutEmailQueue()).to(fanoutExchange());
    }


}
