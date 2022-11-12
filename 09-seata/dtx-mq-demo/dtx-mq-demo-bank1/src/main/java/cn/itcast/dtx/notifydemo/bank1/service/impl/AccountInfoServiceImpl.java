package cn.itcast.dtx.notifydemo.bank1.service.impl;


import cn.itcast.dtx.notifydemo.bank1.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.bank1.dao.AccountInfoDao;
import cn.itcast.dtx.notifydemo.bank1.mq.RabbitMqSender;
import cn.itcast.dtx.notifydemo.bank1.service.AccountInfoService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
@Slf4j
@AllArgsConstructor
public class AccountInfoServiceImpl implements AccountInfoService {


    private AccountInfoDao accountInfoDao;


    private RabbitTemplate rabbitTemplate;


    private AccountChangeEventMapper accountChangeEventMapper;

    private RabbitMqSender rabbitMqSender;

    private TransactionTemplate transactionalTemplate;


    /**
     * 问题：
     *
     * @param accountChangeEvent
     * @Transactional 放在一个大的方法里面会存在连接资源占用的问题.会锁定资源较长时间不释放
     * 解决问题，使用编程事务
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {

        //1 要扣款，操作本地的账户表
        //扣减金额

        // 使用编程式事务缩小范围
        Object row = transactionalTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                int count = accountInfoDao.updateAccountBalance(accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount().multiply(new BigDecimal(-1)));
                if (count == 1) {
                    // 如果扣款成功，就往消息表里面插入消息
                    log.info("扣款成功，持久化消息");
                    return accountChangeEventMapper.insert(accountChangeEvent);
                }else {
                    throw new RuntimeException("扣款失败，请检查"+accountChangeEvent.toString());
                }
            }


        });

        // 如果持久化事务成功，那么就发消息给其他服务
        if (row == Integer.valueOf(1)) {
            // 3、mq发消息,通知其他服务发消息
            rabbitMqSender.sendMsg(Constant.MESSAGE_EXCHANGE, Constant.ROUTING_KEY, accountChangeEvent);
        }
    }

    /**
     * 将消息设置为已消费状态
     * 什么时候设置？ 消费来自被转账方的消息的时候
     *
     * @param param param
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessage(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        int messageId = jsonObject.getInteger("no");
        AccountChangeEvent msgDuplication = new AccountChangeEvent();
        msgDuplication.setTxNo(String.valueOf(messageId));
        msgDuplication.setState(Constant.CONSUMER_STATE);
        accountChangeEventMapper.updateById(msgDuplication);
    }

}
