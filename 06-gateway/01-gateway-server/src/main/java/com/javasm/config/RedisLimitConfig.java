package com.javasm.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-15:14
 * @Description:
 * 自定义请求限流接口
 */
@Configuration
public class RedisLimitConfig {

    @Bean
    // 针对某个接口 ip  来限流
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostString());
    }



    @Bean
    @Primary
    // 针对某个路径（api）  限流
    // api网关
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}
