package cn.itcast.dtx.notifydemo.bank2.config;

import cn.itcast.dtx.notifydemo.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-23:42
 * Description:
 */
@Configuration
public class RabbitMqConfig {

    @Bean("message")
    public Queue queueMessage(){
        return new Queue(Constant.MESSAGE_RESPONSE_QUEUE);
    }


    @Bean
    public TopicExchange exchange(){
        return ExchangeBuilder.topicExchange(Constant.MESSAGE_RESPONSE_EXCHANGE).build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueMessage()).to(exchange()).with(Constant.RESPONSE_ROUTING_KEY);
    }
}
