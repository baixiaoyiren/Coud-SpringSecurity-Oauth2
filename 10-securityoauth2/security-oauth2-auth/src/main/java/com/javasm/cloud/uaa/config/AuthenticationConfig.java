package com.javasm.cloud.uaa.config;

import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.uaa.utils.WebUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-05-16:39
 * Description: oauth2之认证服务配置类
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthenticationConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenEnhancer tokenEnhancer;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final TokenStore jwtTokenStore;

    private final DataSource dataSource;



    /**
     * 允许表单认证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        //security.allowFormAuthenticationForClients();
        security.accessDeniedHandler(new AccessDeniedHandler() {
            //认证失败
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                WebUtils.accessDeniedHandler(response,new Response(ResultCode.UNAUTHORIZED));
            }
        });
        security.authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                WebUtils.accessDeniedHandler(response,new Response(ResultCode.FORBIDDEN));
            }
        });
    }

    /**
     * 授权服务器配置
     *
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *
     *
     *
     * ClientDetailsServiceConfigurer 主要是注入ClientDetailsService实例对象
     * (AuthorizationServerConfigurer 的一个回调配置项，唯一配置注入) 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService），
     * 系统提供的二个ClientDetailsService实现类：JdbcClientDetailsService、InMemoryClientDetailsService。
     *
     * Spring Security OAuth2的配置方法是编写@Configuration类继承AuthorizationServerConfigurerAdapter，
     * 然后重写void configure(ClientDetailsServiceConfigurer clients)方法
     *
     * ClientDetailsServiceConfigurer 能够使用内存或 JDBC 方式实现获取已注册的客户端详情
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        clientId：（必须的）第三方用户的id（可理解为账号）---第三方账户
        clientSecret：第三方应用和授权服务器之间的安全凭证(可理解为密码) -----第三方密码
        scope：指定客户端申请的权限范围,可选值包括read,write,trust;其实授权赋予第三方用户可以在资源服务器获取资源，第三方访问资源的一个权限，访问范围。----第三方的可访问范围
        resourceIds：客户端所能访问的资源id集合
        authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
        accessTokenValiditySeconds：设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).
        refreshTokenValiditySeconds：设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).
         */
        //clients
        //        .inMemory()
        //        .withClient("admin")
        //        .secret(passwordEncoder.encode("234567"))
        //        .scopes("all")
        //        .authorizedGrantTypes("password", "refresh_token")
        //        .accessTokenValiditySeconds(3600)
        //        .refreshTokenValiditySeconds(86400);


        //关于认证客户端相关信息如果均存储在内存中，服务一旦重启，即随即丢失，非常不利于维护。在实际项目中，势必要持久化存储。

        // 配置方法1，只需配置DataSource即可，其它交给框架自动配置,当然数据库需要有表
        clients.withClientDetails(clientDetails());


        // 配置方法2，构建ClientDetailsServiceBuilder
        //clients.configure(jdbcClientDetailsServiceBuilder());

        // 配置方法3，使用ClientDetailsServiceBuilder构建ClientDetailsService
        //clients.withClientDetails(jdbcClientDetailsService());

        // 配置方法4，自定义ClientDetailsService
        //clients.withClientDetails(myClientDetailsService());
    }

    @Bean
    public ClientDetailsService clientDetails(){
        return new JdbcClientDetailsService(dataSource);
    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(tokenEnhancer);
        delegates.add(jwtAccessTokenConverter);

        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //配置加载用户信息的服务
                .tokenStore(jwtTokenStore)
                // 配置存储策略
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain)
                // 自定义的获取token的方法必须要设置，不然会报错
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,
                        HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE);
                //.pathMapping("/auth/oauth/token", "/auth/getToken");
    }
}
