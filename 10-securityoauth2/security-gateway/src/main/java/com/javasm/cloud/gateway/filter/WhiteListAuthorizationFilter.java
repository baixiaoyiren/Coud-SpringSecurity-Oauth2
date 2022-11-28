package com.javasm.cloud.gateway.filter;

import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * 白名单路径访问时需要移除请求头认证信息
 *
 * @author auth
 */
@Component
@Slf4j
public class WhiteListAuthorizationFilter implements WebFilter, Ordered {

    @Resource
    private IgnoreUrlUtils ignoreUrlUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        PathMatcher pathMatcher = new AntPathMatcher();
        // 白名单路径移除请求头认证信息
        List<String> urls = ignoreUrlUtils.ignoreUrlByRedis();
        for (String url : urls) {
            if (pathMatcher.match(url, path)) {
                request = exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}