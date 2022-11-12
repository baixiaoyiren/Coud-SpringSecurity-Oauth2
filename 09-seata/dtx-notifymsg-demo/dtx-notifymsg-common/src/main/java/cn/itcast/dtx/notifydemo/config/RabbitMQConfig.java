package cn.itcast.dtx.notifydemo.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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



}