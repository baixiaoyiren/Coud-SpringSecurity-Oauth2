package cn.itcast.dtx.notifydemo.bank2.message;

import cn.itcast.dtx.notifydemo.bank2.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.bank2.service.AccountInfoService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
@Slf4j
public class TxmsgConsumer {

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    AccountChangeEventMapper accountChangeEventMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;


    //接收消息   增加金额  返回成功消息给生产者
    @RabbitListener(queues = Constant.MESSAGE_QUEUE)
    public void onMessage(Message message) {

        log.info("开始消费消息:{}",message);
        //解析消息
        String str = JSONObject.parseObject(message.getBody(), String.class);
        log.info("JSONObject:{}",str);
        AccountChangeEvent accountChangeEvent = JSONObject.parseObject(str,AccountChangeEvent.class);
        // 接收消息，做消息的处理工作
        boolean flag = accountInfoService.addAccountInfoBalance(accountChangeEvent);
        JSONObject jsonObject1 = new JSONObject();
        // 如果入账成功，则给对方账户应答
        if (flag){
            log.info("转账成功");
            jsonObject1.put("accountChangeEvent",accountChangeEvent);
            jsonObject1.put("statusCode",HttpStatus.OK.value());
            // 发送成功消息给对方
            rabbitTemplate.convertAndSend(Constant.MESSAGE_RESPONSE_EXCHANGE,Constant.RESPONSE_ROUTING_KEY,jsonObject1);
        }else {
            log.error("入账失败");
            jsonObject1.put("statusCode", HttpStatus.BAD_REQUEST.value());
            rabbitTemplate.convertAndSend(Constant.MESSAGE_RESPONSE_EXCHANGE,Constant.RESPONSE_ROUTING_KEY,jsonObject1);
        }

    }
}
