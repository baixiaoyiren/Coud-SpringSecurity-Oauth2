package com.javasm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-20:49
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient // 开启客户端的功能
public class EurekaClient02Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClient02Application.class,args);
    }
}
