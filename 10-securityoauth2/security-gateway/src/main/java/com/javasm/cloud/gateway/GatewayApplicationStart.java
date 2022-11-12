package com.javasm.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-04:11
 * Description:
 */
@SpringBootApplication(scanBasePackages = {"com.*"})
@EnableDiscoveryClient
public class GatewayApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationStart.class,args);
    }
}
