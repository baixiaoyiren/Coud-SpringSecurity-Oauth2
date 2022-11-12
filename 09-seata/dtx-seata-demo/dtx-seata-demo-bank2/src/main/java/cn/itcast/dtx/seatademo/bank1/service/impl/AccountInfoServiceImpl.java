package cn.itcast.dtx.seatademo.bank1.service.impl;

import cn.itcast.dtx.seatademo.bank1.dao.AccountInfoDao;
import cn.itcast.dtx.seatademo.bank1.service.AccountInfoService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    private Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);
    @Autowired
    AccountInfoDao accountInfoDao;

    @Override
    @Transactional
    public void updateAccountBalance(String accountNo, Double amount) {
        logger.info("******** Bank2 Service Begin ... xid: {}", RootContext.getXID());
        //李四增加金额
        accountInfoDao.updateAccountBalance(accountNo, amount);
        //制造异常
        if (amount == 3) {
            //  RootContext 拿到全局事务的上下文
            throw new RuntimeException("bank2 make exception 3,XID:"+RootContext.getXID());
        }
    }
}
