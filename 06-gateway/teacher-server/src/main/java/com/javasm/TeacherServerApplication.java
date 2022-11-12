package com.javasm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-12:46
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class TeacherServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherServerApplication.class,args);
    }
}
