package com.javasm.nacos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:31
 * @Description:
 */
@FeignClient(value = "nacos-user-client-02")
public interface TestFeign {
    @GetMapping("info")
    public String info();
}
