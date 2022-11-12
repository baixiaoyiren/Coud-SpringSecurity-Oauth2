package com.javasm.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:05
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启服务发现客户端
@EnableFeignClients
public class NacosClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class,args);
    }
}
