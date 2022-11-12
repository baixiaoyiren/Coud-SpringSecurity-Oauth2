package com.javasm.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-07:06
 * Description:
 */
@SpringBootApplication(scanBasePackages = "com.*")
@EnableDiscoveryClient
public class OrderApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplicationStart.class,args);
    }
}
