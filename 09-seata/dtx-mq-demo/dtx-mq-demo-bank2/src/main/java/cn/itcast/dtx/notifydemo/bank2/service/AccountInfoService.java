package cn.itcast.dtx.notifydemo.bank2.service;


import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;

/**
 * Created by Administrator.
 */
public interface AccountInfoService {

    //更新账户，增加金额
    public boolean addAccountInfoBalance(AccountChangeEvent accountChangeEvent);
}
