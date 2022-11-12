package cn.itcast.dtx.notifymsg.pay.service.impl;


import cn.itcast.dtx.notifydemo.entity.AccountPay;
import cn.itcast.dtx.notifydemo.entity.Constant;
import cn.itcast.dtx.notifymsg.pay.dao.AccountPayDao;
import cn.itcast.dtx.notifymsg.pay.service.AccountPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
@Slf4j
public class AccountPayServiceImpl implements AccountPayService {

    @Autowired
    AccountPayDao accountPayDao;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //插入充值记录
    @Override
    public AccountPay insertAccountPay(AccountPay accountPay) {
        accountPay.setResult("success");
        if (accountPay.getAccountNo() == null || accountPay.getPayAmount() == null){
            return null;
        }
        int success = accountPayDao.insert(accountPay);
        if(success>0){
            //充值完成要发送结果通知
            //发送通知,使用主题消息发送通知
            accountPay.setResult("success");
            rabbitTemplate.convertAndSend(Constant.MESSAGE_EXCHANGE,Constant.ROUTING_KEY,accountPay);
            return accountPay;
        }
        return null;
    }

    //查询充值记录，接收通知方调用此方法来查询充值结果
    @Override
    public AccountPay getAccountPay(String txNo) {
        AccountPay accountPay = accountPayDao.selectById(txNo);
        return accountPay;
    }
}
