package cn.itcast.dtx.seatademo.bank1.service.impl;

import cn.itcast.dtx.seatademo.bank1.dao.AccountInfoDao;
import cn.itcast.dtx.seatademo.bank1.feign.Back2Client;
import cn.itcast.dtx.seatademo.bank1.service.AccountInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-16:42
 * Description:
 */
@Service
@Slf4j
@GlobalTransactional // 开启全局日志
@AllArgsConstructor
public class AccountInfoServiceImpl implements AccountInfoService {

    private AccountInfoDao accountInfoDao;

    private Back2Client back2Client;


    @Transactional(rollbackFor = Exception.class) // 本地事务
    @GlobalTransactional(rollbackFor = Exception.class,timeoutMills = 12000) // 全局事务
    @Override
    public void updateAccountBalance(String accountNo, Double amount) {
        // 调用李四的微服务进行转账
        String transfer = back2Client.transfer(amount);

        if (amount.equals(new Double(11.0))){
            throw new RuntimeException("不支持");
        }
        // 张三扣减金额
        accountInfoDao.updateAccountBalance(accountNo,amount*-1);
        if ("请求失败".equals(transfer)){
            // 调用李四的微服务异常
            throw new RuntimeException("调用李四微服务异常");
        }
    }
}
