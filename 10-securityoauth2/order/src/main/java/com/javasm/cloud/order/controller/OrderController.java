package com.javasm.cloud.order.controller;

import com.javasm.cloud.common.entity.MyAuthentication;
import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-07:08
 * Description:
 */
@RestController
@Slf4j
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;


    @RequestMapping("/test")
    @Permission(MyAuthentication.QUERY)
    public Response test(@RequestParam String account){
        return orderService.getOrder(account);
    }

    @RequestMapping("/test1")
    @Permission(MyAuthentication.UPDATE)
    public Response test2(){
        int i = 1/0;
        return new Response(ResultCode.SUCCESS).data("成功");
    }

    @SneakyThrows
    @RequestMapping("/test2")
    public Response test3(){
        TimeUnit.SECONDS.sleep(2);
        return new Response(ResultCode.SUCCESS);
    }
}
