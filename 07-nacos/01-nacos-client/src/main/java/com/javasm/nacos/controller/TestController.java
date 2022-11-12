package com.javasm.nacos.controller;

import com.javasm.nacos.feign.TestFeign;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:18
 * @Description: 手动发现服务  跨服务空间、组的服务不能相互调用
 */
@RestController
@AllArgsConstructor
public class TestController {
    private DiscoveryClient discoveryClient;

    private TestFeign feign;

    @GetMapping("test")
    public String test(){
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-user-client-02");
        System.out.println(instances);
        return feign.info();
    }


}
