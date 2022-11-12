package com.javasm.feign.hystrix;

import com.javasm.domain.Order;
import com.javasm.feign.UserOrderFeign;
import org.springframework.stereotype.Component;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-11:33
 * @Description:
 */
@Component
public class UserOrderFeignHystrix implements UserOrderFeign {
    /**
     * 一般远程调用的熔断可以直接返回null
     *
     * @param userId
     * @return
     */
    @Override
    public Order getOrderUserById(Integer userId) {
        return null;
    }
}
