package com.javasm.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.gateway.utils.WebFluxUtils;
import com.nimbusds.jose.JWSObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 * 这里为什么要解析用户信息？
 * 比如你的请求里面需要根据当前用户的信息去获取到相关的资源，比如根据用户信息获取用户的昵称之类的
 *
 * @author Honghui [wanghonghui_work@163.com] 2021/3/16
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthGlobalFilter implements WebFilter, Ordered {

    private RedisCache redisCache;

    private IgnoreUrlUtils ignoreUrlUtils;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            // 从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            // 验证token是否已经退出登录了,白名单的路径就跳过
            String key = Constant.TOKEN_TIME + realToken;
            Object num = redisCache.getCacheObject(key);
            String path = exchange.getRequest().getPath().toString();
            if (!ignoreUrlUtils.ignoreUrlByRedis().contains(path)) {
                log.info("path:{}", path);
                // 如果黑名单当中存在，则表示该token已失效
                if (num != null) {
                    ServerWebExchange finalExchange = exchange;
                    return Mono.defer(() -> Mono.just(finalExchange.getResponse()))
                            .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.UNAUTHORIZED));
                }
            }
            if (StringUtils.isNotEmpty(realToken) && realToken.contains("Bearer")){
                JWSObject jwsObject = JWSObject.parse(realToken);
                String userStr = jwsObject.getPayload().toString();
                LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
                ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
                exchange = exchange.mutate().request(request).build();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}