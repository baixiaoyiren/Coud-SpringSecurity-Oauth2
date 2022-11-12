package cn.itcast.dtx.notifydemo.bank1.config;

import cn.itcast.dtx.notifydemo.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-30-16:12
 * Description:
 */
@Configuration
public class MqConfig {
    @Bean("message")
    public Queue queueMessage(){
        return new Queue(Constant.MESSAGE_QUEUE);
    }


    @Bean
    public TopicExchange exchange(){
        return ExchangeBuilder.topicExchange(Constant.MESSAGE_EXCHANGE).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueMessage()).to(exchange()).with(Constant.ROUTING_KEY);
    }
}
