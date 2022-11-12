package com.javasm;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-00:22
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients // 开启feign远程调用功能
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    /**
     * feign打印日志信息
     */

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
