package com.javasm.cloud.gateway.config;

import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.utils.IgnoreUrlUtils;
import com.javasm.cloud.gateway.filter.AuthGlobalFilter;
import com.javasm.cloud.gateway.filter.WhiteListAuthorizationFilter;
import com.javasm.cloud.gateway.utils.WebFluxUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
@Slf4j

public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;
    private final IgnoreUrlUtils ignoreUrlUtils;
    private final WhiteListAuthorizationFilter whiteListRemoveJwtFilter;
    private final AuthGlobalFilter globalFilter;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // 1、自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
        http
                .oauth2ResourceServer()
                .jwt()
                // jwt token转换器
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
                //.publicKey(rsaPublicKey());   // 本地加载公钥
        //.jwkSetUri("http://localhost:9401/auth/rsa/publicKey");  // 远程获取公钥，默认读取的key是spring.security.oauth2.resourceserver.jwt.jwk-set-uri
        // 对白名单路径，直接移除JWT请求头，不移除的话，后台会校验jwt
        http.addFilterBefore(whiteListRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        // 全局过滤器验证token是否黑名单
        http.addFilterBefore(globalFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange()
                // 用户白名单
                .pathMatchers(ignoreUrlUtils.ignoreUrlByRedis().toArray(new String[0])).permitAll()
                .anyExchange().access(authorizationManager) // 鉴权管理器配置
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint()) //处理未认证
                .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
                .and().csrf().disable();

        return http.build();
    }

    /**
     * 自定义未授权响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.FORBIDDEN));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> {
            log.error(e.getMessage());
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.UNAUTHORIZED));
            return mono;
        };
    }

    /**
     *
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     *
     * 转换器setAuthoritiesClaimName方法是可以自定义AuthoritiesClaimName。
     * 注解清晰的写明了设置用于映射权限（Authorities）token负载（token claim）的名字。
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 将权限前缀设置为""
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


    /**
     * 本地获取JWT验签公钥
     */
    //@SneakyThrows
    //@Bean
    //public RSAPublicKey rsaPublicKey() {
    //    Resource resource = new ClassPathResource("public.key");
    //    InputStream is = resource.getInputStream();
    //    String publicKeyData = IoUtil.read(is).toString();
    //    X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));
    //
    //    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    //    RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
    //    return rsaPublicKey;
    //}
}

