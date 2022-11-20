package com.javasm.cloud.gateway.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-21:26
 * Description:
 */
@Configuration
public class WebConfig {

    /**
     *1、HttpMessageConverter，报文信息转换器，将请求报文转换为 Java 对象，或将 Java 对象转换为响应报文
     *
     * 2、两个注解，将请求报文转换为 Java 对象：@RequestBody，@ResponseBody
     *
     * 3、两个类型，将 Java 对象转换为响应报文：RequestEntity、ResponseEntity
     *
     * 而webflux不支持阻塞式，需要手动注入
     *
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
