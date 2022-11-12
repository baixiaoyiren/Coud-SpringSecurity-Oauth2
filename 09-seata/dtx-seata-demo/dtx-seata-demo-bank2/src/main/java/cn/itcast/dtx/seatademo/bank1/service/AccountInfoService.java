package cn.itcast.dtx.seatademo.bank1.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-22:20
 * Description:
 */
public interface AccountInfoService {
    @Transactional
    void updateAccountBalance(String accountNo, Double amount);
}
