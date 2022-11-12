package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.common.utils.RedisCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private RedisCache redisCache;

    private IgnoreUrlUtils ignoreUrlUtils;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
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
        if (CollectionUtils.isEmpty(authorities)){
            return Mono.just(new AuthorizationDecision(true));
        }



        String token = request.getHeaders().getFirst("Authorization");
        // 如果token为空是白名单上的才给通过，否则都需要认证
        if (StringUtils.isEmpty(token)&&ignoreUrlUtils.ignoreUrlByRedis().contains(path)) {
            return Mono.just(new AuthorizationDecision(true));
            // 如果token不以"bearer "为前缀，到此方法里说明JWT无效
        }else if (!StringUtils.startsWithIgnoreCase(token, "Bearer ")){
            return Mono.just(new AuthorizationDecision(false));
        }

        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleId -> {
                    // 5. roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                    log.info("访问路径：{}", path);
                    log.info("用户权限：{}", roleId);
                    log.info("资源需要权限authorities：{}", authorities);
                    return authorities.contains(roleId);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
