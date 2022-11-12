package cn.itcast.dtx.seatademo.bank1.controller;

import cn.itcast.dtx.seatademo.bank1.entity.Response;
import cn.itcast.dtx.seatademo.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-16:55
 * Description:
 */
@RestController
public class BankController {

    @Autowired
    private AccountInfoService accountInfoService;


    // 张三转账
    @GetMapping("/transfer")
    public Response transfer(Double amount){
        long start = System.currentTimeMillis();
        accountInfoService.updateAccountBalance("2",amount);
        return Response.builder().code(HttpStatus.OK.value()).data(amount).message("转账耗时:" + (System.currentTimeMillis()-start)).build();
    }
}
