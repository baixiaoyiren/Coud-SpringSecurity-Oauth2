package cn.itcast.dtx.seatademo.bank1.controller;


import cn.itcast.dtx.seatademo.bank1.entity.Order;
import cn.itcast.dtx.seatademo.bank1.entity.Response;
import cn.itcast.dtx.seatademo.bank1.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("order/create")
    public Response create(Order order) {
        orderService.create(order);
        return new Response(200,"订单OK");
    }
}
