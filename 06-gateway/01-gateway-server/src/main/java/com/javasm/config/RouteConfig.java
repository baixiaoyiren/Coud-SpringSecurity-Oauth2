package com.javasm.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-13:03
 * @Description:
 */
@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/bd")
                        .uri("https://www.baidu.com"))
                .route("bilibli", r -> r.path("/blbl")
                        .uri("https://www.bilibili.com"))
                .build();
    }
}
