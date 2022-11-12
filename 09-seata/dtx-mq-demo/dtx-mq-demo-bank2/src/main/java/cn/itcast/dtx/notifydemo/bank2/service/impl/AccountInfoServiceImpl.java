package cn.itcast.dtx.notifydemo.bank2.service.impl;

import cn.itcast.dtx.notifydemo.bank2.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.bank2.dao.AccountInfoDao;
import cn.itcast.dtx.notifydemo.bank2.service.AccountInfoService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoDao accountInfoDao;

    @Autowired
    AccountChangeEventMapper accountChangeEventMapper;

    //更新账户，增加金额
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccountInfoBalance(AccountChangeEvent accountChangeEvent) {
        // 操作本地账户
        log.info("bank2更新本地账号，账号：{},金额：{}",accountChangeEvent.getAccountNo(),accountChangeEvent.getAmount());

        // 大于0 则说明消息已经被消费并存在于本地了，不做任何处理
        if(accountInfoDao.isExistTx(accountChangeEvent.getTxNo(),Constant.CONSUMER_STATE)>0){
            return false;
        }
        //否则增加金额
        accountInfoDao.updateAccountBalance(accountChangeEvent.getAccountNo(),accountChangeEvent.getAmount());

        //添加事务记录，用于幂等
        accountChangeEvent.setState(Constant.CONSUMER_STATE);
        accountChangeEventMapper.insert(accountChangeEvent);
        if(accountChangeEvent.getAmount().equals(new BigDecimal(4))){
            throw new RuntimeException("人为制造异常");
        }
        return true;
    }
}
