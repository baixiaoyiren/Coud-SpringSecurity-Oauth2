package cn.itcast.dtx.notifydemo.bank1.listener;

import cn.itcast.dtx.notifydemo.bank1.service.AccountChangeEventService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-23:36
 * Description:
 */
@Component
@Slf4j
public class MessageListener {

    @Autowired
    AccountChangeEventService accountChangeEventService;

    @RabbitListener(queues = Constant.MESSAGE_RESPONSE_QUEUE)
    public void process(@NotNull Message message){
        log.info("===========接收转账成功消息===========");
        JSONObject jsonObject = JSONObject.parseObject(new String(message.getBody()));
        if ((Integer) jsonObject.get("statusCode") == HttpStatus.OK.value()){
            AccountChangeEvent accountChangeEvent = jsonObject.getObject("accountChangeEvent",AccountChangeEvent.class);
            accountChangeEventService.updateState(accountChangeEvent);
        }
    }

}
