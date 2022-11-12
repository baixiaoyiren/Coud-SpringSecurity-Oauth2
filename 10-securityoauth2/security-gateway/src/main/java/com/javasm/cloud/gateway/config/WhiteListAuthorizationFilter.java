package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.utils.IgnoreUrlUtils;
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
public class WhiteListAuthorizationFilter implements WebFilter {

    @Resource
    private IgnoreUrlUtils ignoreUrlUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除请求头认证信息
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
}