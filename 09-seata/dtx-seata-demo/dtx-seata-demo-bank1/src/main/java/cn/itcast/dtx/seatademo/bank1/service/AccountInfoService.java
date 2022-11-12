package cn.itcast.dtx.seatademo.bank1.service;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-16:41
 * Description:
 */
public interface AccountInfoService {

    // 张三扣减金额
    public void updateAccountBalance(String accountNo,Double amount);
}
