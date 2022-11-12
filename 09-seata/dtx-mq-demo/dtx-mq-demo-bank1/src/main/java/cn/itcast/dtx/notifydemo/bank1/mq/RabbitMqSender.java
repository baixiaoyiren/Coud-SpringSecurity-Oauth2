package cn.itcast.dtx.notifydemo.bank1.mq;

import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-21:14
 * Description:
 */
@Slf4j
@Component
@AllArgsConstructor
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String exchange, String routingKey, AccountChangeEvent accountChangeEvent){
        try {
            //发送消息给消费者
            log.info("============发送消息扣减金额===================");
            rabbitTemplate.convertAndSend(exchange,routingKey,JSONObject.toJSONString(accountChangeEvent));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
