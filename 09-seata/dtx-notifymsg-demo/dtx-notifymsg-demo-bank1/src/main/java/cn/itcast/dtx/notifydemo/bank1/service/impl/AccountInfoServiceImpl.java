package cn.itcast.dtx.notifydemo.bank1.service.impl;


import cn.itcast.dtx.notifydemo.bank1.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.bank1.dao.AccountInfoDao;
import cn.itcast.dtx.notifydemo.bank1.service.AccountInfoService;
import cn.itcast.dtx.notifydemo.bank1.spring.PayClient;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.AccountInfo;
import cn.itcast.dtx.notifydemo.entity.AccountPay;
import cn.itcast.dtx.notifydemo.entity.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    PayClient payClient;

    @Autowired
    AccountChangeEventMapper accountChangeEventMapper;

    //更新账户金额
    @Override
    @Transactional
    public void updateAccountBalance(AccountChangeEvent accountChange) {
        //幂等校验
        if(accountInfoDao.isExistTx(accountChange.getTxNo())>0){
            return ;
        }
        AccountInfo accountInfo = accountInfoDao.selectById(accountChange.getAccountNo());
        accountInfo.setAccountBalance(accountChange.getAmount().add(accountInfo.getAccountBalance()));
        int i = accountInfoDao.updateById(accountInfo);
        //插入事务记录，用于幂等控制
        accountChangeEventMapper.insert(accountChange);

    }

    //远程调用查询充值结果
    @Override
    public AccountPay queryPayResult(String tx_no) {

        //远程调用
        AccountPay payresult = payClient.payresult(tx_no);
        if("success".equals(payresult.getResult())){
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(payresult.getAccountNo());//账号
            accountChangeEvent.setAmount(payresult.getPayAmount());//金额
            accountChangeEvent.setTxNo(payresult.getId());//充值事务号
            accountChangeEvent.setState(Constant.CONSUMER_STATE);
            updateAccountBalance(accountChangeEvent);
        }
        return payresult;
    }
}
