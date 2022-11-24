package com.javasm.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.exception.MyAuthenticationException;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.gateway.utils.WebFluxUtils;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
 * @author
 */
@Component
@Slf4j
public class AuthGlobalFilter implements WebFilter, Ordered {

    private final RedisCache redisCache;

    private final IgnoreUrlUtils ignoreUrlUtils;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    private final ThreadPoolTaskExecutor executor;

    private final TokenStore tokenStore;


    @Lazy
    public AuthGlobalFilter(RedisCache redisCache, IgnoreUrlUtils ignoreUrlUtils, ThreadPoolTaskExecutor executor, TokenStore tokenStore) {
        this.redisCache = redisCache;
        this.ignoreUrlUtils = ignoreUrlUtils;

        this.executor = executor;
        this.tokenStore = tokenStore;
    }

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
            if (StringUtils.isNotEmpty(realToken)&&token.contains("Bearer")){
                // 检查是不是刷新token，直接配置tokenstore调用本地方法，减少网络IO，推荐
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(realToken);
                JWSObject jwsObject = JWSObject.parse(realToken);
                String userStr = jwsObject.getPayload().toString();
                LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
                LOGGER.info("AuthGlobalFilter.filter() oAuth2AccessToken expireTime:{},getAdditionalInformation:{}", oAuth2AccessToken.getExpiration(),oAuth2AccessToken.getAdditionalInformation());
                ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
                exchange = exchange.mutate().request(request).build();
            }
        } catch (ParseException e) {
            throw new MyAuthenticationException("格式转换错误:"+e.getMessage());

        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}