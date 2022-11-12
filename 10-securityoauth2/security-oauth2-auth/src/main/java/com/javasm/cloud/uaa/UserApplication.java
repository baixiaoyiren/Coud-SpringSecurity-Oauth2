package com.javasm.cloud.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-16:12
 * Description:
 */
@SpringBootApplication(scanBasePackages = {"com.*"})
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.javasm.cloud.uaa.mapper"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
