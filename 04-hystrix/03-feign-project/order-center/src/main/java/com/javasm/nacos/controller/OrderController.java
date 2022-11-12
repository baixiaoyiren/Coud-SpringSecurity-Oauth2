package com.javasm.nacos.controller;

import com.javasm.domain.Order;
import com.javasm.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-11:22
 * @Description:
 */
@RestController
public class OrderController implements UserOrderFeign {
    @Override
    public Order getOrderUserById(Integer userId) {
        Order order = Order.builder().orderId(userId).name("鱼香肉丝").price(12.2).build();

        return order;
    }
}
