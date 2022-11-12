package cn.itcast.dtx.notifydemo.bank1.service;


import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-20:15
 * Description:
 */
public interface AccountChangeEventService {
    int updateState(AccountChangeEvent msg);
}
