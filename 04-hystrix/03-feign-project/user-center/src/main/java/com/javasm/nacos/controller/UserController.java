package com.javasm.nacos.controller;

import com.javasm.domain.Order;
import com.javasm.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-11:31
 * @Description:
 */
@RestController
public class UserController {
    @Autowired
    public UserOrderFeign orderFeign;

    @GetMapping("find")
    public Order findOrder(){
        return orderFeign.getOrderUserById(1);
    }
}
