package com.javasm.cloud.gateway;

import com.javasm.cloud.common.utils.AuthenticationRedisUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-04:11
 * Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 开启feign客户端，扫描带有@FFeignClient注解的接口，通过动态代理生成http请求
// 排除加载AuthenticationRedisUtils.class,因为该类依赖了web组件，所以需要排除
@ComponentScan(basePackages = "com.*",excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {AuthenticationRedisUtils.class})})
public class GatewayApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationStart.class,args);
    }



}
