package com.javasm.feign;

import com.javasm.domain.Order;
import com.javasm.feign.hystrix.UserOrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-11:22
 * @Description:
 */
@FeignClient(value = "order-service",fallback = UserOrderFeignHystrix.class)
public interface UserOrderFeign {

    // 查询订单
    @GetMapping("/order/getOrderUserById")
    Order getOrderUserById(@RequestParam("id") Integer userId);
}
