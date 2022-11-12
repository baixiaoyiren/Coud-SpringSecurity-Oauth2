package com.javasm.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-00:26
 * @Description:
 */
@RestController
public class OrderController {

    @GetMapping("doOrder")
    public String doOrder() throws InterruptedException {
        // 模拟超时
        TimeUnit.SECONDS.sleep(2);
        return "榨汁油条";
    }
}
