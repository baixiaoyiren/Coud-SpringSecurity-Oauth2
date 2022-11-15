package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
public class WhiteListAuthorizationFilter implements WebFilter {

    @Resource
    private IgnoreUrlUtils ignoreUrlUtils;

    @Resource
    private RedisCache redisCache;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        //3、获取请求头参数token,查看token是否失效
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isNotEmpty(token) &&token.contains("Bearer ")){

            log.info(token);
            String key = Constant.TOKEN_TIME + token.split(" ")[1];
            Object object = redisCache.getCacheObject(key);
            if (object == null){
                ServerWebExchange finalExchange = exchange;
                Mono<Void> mono = Mono.defer(() -> Mono.just(finalExchange.getResponse()))
                        .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.EXPIRED));
                return mono;
            }
        }

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