package com.javasm.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:05
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启服务发现客户端
public class NacosClient01Application {
    public static void main(String[] args) {
        SpringApplication.run(NacosClient01Application.class,args);
    }
}
