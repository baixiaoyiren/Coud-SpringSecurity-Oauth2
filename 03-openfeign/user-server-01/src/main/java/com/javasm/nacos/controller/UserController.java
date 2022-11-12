package com.javasm.nacos.controller;

import com.javasm.entity.Order;
import com.javasm.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-00:26
 * @Description:
 */
@RestController
public class UserController {

    @Autowired
    private UserOrderFeign orderFeign;

    @GetMapping("userDoOrder")
    public String userDoOrder(){
        System.out.println("有用户进来了....");
        // 这里需要发起远程调用
        return orderFeign.doOrder();
    }


    @GetMapping("testParam")
    public String testParam(){
        String xxx = orderFeign.testUrl("xxx", 12);
        System.out.println(xxx);

        String boss = orderFeign.oneParam("老板");
        System.out.println(boss);

        String lg = orderFeign.twoParam("劳保", 43);
        System.out.println(lg);

        Order order = Order.builder().name("牛排").id(1).price(12.3).time(new Date()).build();
        String obj = orderFeign.oneObj(order);
        System.out.println(obj);

        String param = orderFeign.oneObjOneParam(order, "老板");
        System.out.println(param);

        return "ok";
    }


    @GetMapping("testTime")
    public String testTime(){
        Date date = new Date();
        System.out.println(date);
        String status = orderFeign.testTime(date);
        System.out.println(status);
        return status;
    }

}
