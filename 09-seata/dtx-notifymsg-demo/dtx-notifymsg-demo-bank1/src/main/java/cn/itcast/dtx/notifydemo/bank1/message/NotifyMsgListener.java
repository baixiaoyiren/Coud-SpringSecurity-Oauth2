package cn.itcast.dtx.notifydemo.bank1.message;

import cn.itcast.dtx.notifydemo.bank1.service.AccountInfoService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.AccountPay;
import cn.itcast.dtx.notifydemo.entity.Constant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-30-15:50
 * Description:
 */

@Component
@Slf4j
@AllArgsConstructor
public class NotifyMsgListener{

    private AccountInfoService accountInfoService;

    @RabbitListener(queues = {Constant.MESSAGE_QUEUE})
    public void listen(Message message){
        AccountPay payresult = JSONObject.parseObject(message.getBody(), AccountPay.class);
        // 接收与消息
        if("success".equals(payresult.getResult())){
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(payresult.getAccountNo());//账号
            accountChangeEvent.setAmount(payresult.getPayAmount());//金额
            accountChangeEvent.setTxNo(payresult.getId());//充值事务号
            accountChangeEvent.setState(Constant.CONSUMER_STATE);
            accountInfoService.updateAccountBalance(accountChangeEvent);
            log.info("消息处理完成:{}", JSON.toJSONString(accountChangeEvent));
        }
    }
}
