package com.javasm.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-22:41
 * @Description:
 */
@RestController
public class DiscoveryController {

    // 用于服务发现的
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 通过应用名称找到服务的端口和ip
     *
     * @param serverName serverName
     * @return return
     */
    @GetMapping("test")
    public String doDiscovery(String serverName){
        // 这是服务发现 通过服务的应用名称找到服务的具体信息
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        //
        instances.forEach(System.out::println);
        ServiceInstance serviceInstance = instances.get(0);
        int port = serviceInstance.getPort();
        String host = serviceInstance.getHost();
        System.out.println(host+":"+port);
        return instances.get(0).toString();
    }
}
