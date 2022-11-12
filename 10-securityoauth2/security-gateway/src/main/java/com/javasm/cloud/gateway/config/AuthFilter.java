package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.gateway.utils.WebFluxUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关-全局过滤器
 * 1、实现两个接口和两个方法
 * 2、在filter方法中，完成过滤逻辑
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisCache redisCache;

    /**
     * 核心过滤方法：业务处理
     * @param exchange：请求上下文（获取request和response）
     * @param chain：过滤器链（控制程序放行）
     * @return
     */
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //3、获取请求头参数token
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isNotEmpty(token) &&token.contains("Bearer ")){

            log.info(token);
            String key = Constant.TOKEN_TIME + token.split(" ")[1];
            Object object = redisCache.getCacheObject(key);
            if (object == null){
                Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                        .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.EXPIRED));
                return mono;
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 指定多个过滤器时，此过滤器的执行顺序
     */
    public int getOrder() {
        return -1000;
    }
}