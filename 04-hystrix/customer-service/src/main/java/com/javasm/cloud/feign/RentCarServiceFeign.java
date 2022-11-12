package com.javasm.cloud.feign;

import com.javasm.cloud.feign.hystrix.RentCarServiceFeignImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-06:01
 * @Description:
 */

// fallback指定熔断类
@FeignClient(value = "rent-car-service",fallback = RentCarServiceFeignImpl.class)
public interface RentCarServiceFeign {

    @GetMapping("rent")
    public String rent();
}
