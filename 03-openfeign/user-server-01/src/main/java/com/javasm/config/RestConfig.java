package com.javasm.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-03-07:47
 * Description:
 */
@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced // 负载均衡  必须使用注册中心上的服务名 不能用ip或域名
    @ConditionalOnMissingBean({RestTemplate.class})
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
