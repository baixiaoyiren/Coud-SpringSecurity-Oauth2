package com.javasm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-23:41
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @Bean
    // 加上这个注解，ribbon会自己操作负载均衡，平摊到同一组服务的不同机器上
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
