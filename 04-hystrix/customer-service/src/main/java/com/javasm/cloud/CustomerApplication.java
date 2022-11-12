package com.javasm.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-05:52
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients // 开启feign远程调用
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class,args);
    }
}
