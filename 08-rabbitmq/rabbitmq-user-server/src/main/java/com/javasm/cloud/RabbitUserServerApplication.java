package com.javasm.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:41
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class RabbitUserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitUserServerApplication.class,args);
    }
}
