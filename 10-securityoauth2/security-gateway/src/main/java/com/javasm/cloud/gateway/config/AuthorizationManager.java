package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.utils.RedisCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 在 WebFluxSecurity 中自定义鉴权操作需要实现 ReactiveAuthorizationManager 接口
 *
 *  鉴权管理器是作为资源服务器验证是否有权访问资源的裁决者
 *
 *  权限管理
 * @author admin
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class AuthorizationManager{

    private RedisCache redisCache;

    @Bean
    public ReactiveAuthorizationManager<AuthorizationContext> reactiveAuthorizationManager(){
        return (mono,authorizationContext)->{
            ServerHttpRequest request = authorizationContext.getExchange().getRequest();
            String path = request.getURI().getPath();

            // 1. 对应跨域的预检请求直接放行
            if (request.getMethod() == HttpMethod.OPTIONS) {
                return Mono.just(new AuthorizationDecision(true));
            }

            /**
             * 鉴权开始
             *
             * 缓存取 [URL权限-角色集合]
             */
            List<String> authorities =  redisCache.getCacheMapValue(Constant.PATH_PERMISSION,path);
            // 权限为空，给它放行，表明是该访问路径还没有添加任何权限点，安全级别最低
            if (CollectionUtils.isEmpty(authorities)){
                return Mono.just(new AuthorizationDecision(true));
            }

            // 2、认证通过且角色匹配的用户可访问当前路径
            return mono
                    //isAuthenticated 返回的是是否认证成功
                    .filter(Authentication::isAuthenticated)
                    .flatMapIterable(Authentication::getAuthorities)
                    .map(GrantedAuthority::getAuthority)
                    .any(authorities::contains)
                    .map(AuthorizationDecision::new)
                    .defaultIfEmpty(new AuthorizationDecision(false));
        };
    }


}
