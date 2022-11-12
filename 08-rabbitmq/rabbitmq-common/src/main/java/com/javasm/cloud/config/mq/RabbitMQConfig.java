package com.javasm.cloud.config.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 自定义rabbitmq 配置
 *
 * @author admin
 */
@Component
@Slf4j
public class RabbitMQConfig {


    //MQ配置的时候，如果配置了json解析器,则程序会走自动确认消费，配置文件的配置就不生效了,这个问题解决办法就是重写一下这个东东，用代码设置手动确认
    @Bean("rabbitListenerContainerFactory")
    @Primary
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
                messageProperties.setContentType("application/json");
                return new Message(JSON.toJSONBytes(object), messageProperties);
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                return JSON.parse(message.getBody());
            }
        });
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }


    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirm());
        rabbitTemplate.setReturnCallback(returnedMessage());
        rabbitTemplate.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
                messageProperties.setContentType("application/json");
                return new Message(JSON.toJSONBytes(object), messageProperties);
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                return JSON.parse(message.getBody());
            }
        });
        return rabbitTemplate;
    }


    public RabbitTemplate.ConfirmCallback confirm() {
        return (CorrelationData correlationData, boolean ack, String cause) -> {
            //if (Objects.nonNull(correlationData)) {
            //    if (Objects.nonNull(correlationData.getReturnedMessage())
            //            && Objects.nonNull(correlationData.getReturnedMessage().getMessageProperties())
            //            && Objects.nonNull(correlationData.getReturnedMessage().getMessageProperties().getReceivedDelay())) {
            //        Integer delay = correlationData.getReturnedMessage().getMessageProperties().getReceivedDelay();
            //        //如果是延迟队列就忽视掉
            //        if (Objects.nonNull(delay) && delay != 0) {
            //            return;
            //        }
            //    }
            //}

            //ack为true则消息已发送  否则消息发送失败
            if (!ack) {
                log.info("消息已发送至exchange");
            } else {
                log.error("消息未发送至exchange");
            }
        };
    }


    public RabbitTemplate.ReturnCallback returnedMessage() {
        return (Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            // 如果是延迟队列，则一定会调用该方法进行返回，因此要在此做判断是不是延迟交换机
            if (exchange.contains("delay")) {
                return;
            }
            log.error("消息被退回，具体信息为msg:{},replyCode:{},replyText:{},exchange:{},routeKey:{}",
                    message.getBody(), replyCode, replyText, exchange, routingKey);
        };
    }
}