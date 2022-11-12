package cn.itcast.dtx.notifydemo.bank1.service;


import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;

/**
 * Created by Administrator.
 */
public interface AccountInfoService {

    //向mq发送转账消息
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent);

    // 修改消息的状态
    public void updateMessage(String params);

}
