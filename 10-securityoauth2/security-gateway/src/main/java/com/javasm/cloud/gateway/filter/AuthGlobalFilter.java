package com.javasm.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.exception.MyAuthenticationException;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.gateway.feign.AuthFeignClient;
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
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    private final AuthFeignClient authFeignClient;

    private final ThreadPoolTaskExecutor executor;


    @Lazy
    public AuthGlobalFilter(RedisCache redisCache, IgnoreUrlUtils ignoreUrlUtils, AuthFeignClient authFeignClient, ThreadPoolTaskExecutor executor) {
        this.redisCache = redisCache;
        this.ignoreUrlUtils = ignoreUrlUtils;
        this.authFeignClient = authFeignClient;
        this.executor = executor;
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
                // 检查是不是刷新token  这个也可以在各个服务过滤器里面实现远程调用进行判断，这里不好的地方就是fegin请求是阻塞式的，会使用多线程额外处理
                //远程调用，多线程调用，带有返回值的，因此会阻塞
                Response response = executor.submit(() -> authFeignClient.convertAccessToken(realToken)).get(30, TimeUnit.SECONDS);
                // 远程调用，使用异步调用
                //CompletionStage接口定义了任务编排的方法，执行某一阶段，可以向下执行后续阶段。
                // 异步执行的，默认线程池是ForkJoinPool.commonPool()，但为了业务之间互不影响，且便于定位问题，强烈推荐使用自定义线程池。
                //Response response = CompletableFuture.supplyAsync(() -> authFeignClient.convertAccessToken(realToken)).get();
                if (response.getData() == null || response.getCode() == ResultCode.BADREQUEST.getCode()){
                    throw new InvalidTokenException(response.getMsg());
                }
                JWSObject jwsObject = JWSObject.parse(realToken);
                String userStr = jwsObject.getPayload().toString();
                LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
                ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
                exchange = exchange.mutate().request(request).build();
            }
        } catch (ParseException e) {
            throw new MyAuthenticationException("格式转换错误"+e.getMessage());

        } catch (ExecutionException e) {
            throw new MyAuthenticationException("线程异常....."+e.toString());
        } catch (InterruptedException e) {
            throw new MyAuthenticationException("线程被中断"+e.toString());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}