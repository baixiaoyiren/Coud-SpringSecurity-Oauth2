package cn.itcast.dtx.notifydemo.bank1.config;

import cn.itcast.dtx.notifydemo.bank1.service.AccountChangeEventService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-20:00
 * Description: rabbitmq的confirm模式和return模式开启
 */
@Configuration
@Component
@AllArgsConstructor
@Slf4j
public class rabbitmqConfig {



    private AccountChangeEventService accountChangeEventService;

    private RabbitTemplate rabbitTemplate;


    public RabbitTemplate.ConfirmCallback confirm() {
        return (CorrelationData correlationData, boolean ack, String cause)->{
            // ack未false则表示消息没有收到应答，消费者未消费消息,直接返回
            // 为true则表示已收到消息，则把数据库当中的持久化消息改为已消费
            if (ack&&correlationData!= null){
                // 修改消息持久化状态 0 未消费 1已消费
                AccountChangeEvent msgDuplication = new AccountChangeEvent();
                msgDuplication.setTxNo(correlationData.getId());
                log.info("修改本地状态并更新");
                msgDuplication.setState("1");
                int count = accountChangeEventService.updateState(msgDuplication);
                if (count <= 0){
                    // 状态修改失败的事务会重新发起一个定时任务去做扫描重新发送
                    log.warn("本地消息状态修改失败");
                }
            }
        };
    }


    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirm());
        rabbitTemplate.setMandatory(true);
    }

    @Bean("message")
    public Queue queueMessage(){
        return new Queue(Constant.MESSAGE_QUEUE);
    }


    @Bean
    public TopicExchange exchange(){
        return ExchangeBuilder.topicExchange(Constant.MESSAGE_EXCHANGE).build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueMessage()).to(exchange()).with(Constant.ROUTING_KEY);
    }



}
