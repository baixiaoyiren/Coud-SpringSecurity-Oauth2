package com.javasm.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-05:52
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class RentCarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentCarServiceApplication.class,args);
    }
}
