package com.javasm.cloud.uaa.config;

import com.javasm.cloud.common.utils.AuthenticationRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:29
 * Description: 资源服务器
 */
@EnableResourceServer
@Configuration
@Slf4j
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationRedisUtils redisUtils;

    @Override
    public void configure(HttpSecurity http) throws Exception {


        http
                //将请求与任何Endpoint进行匹配，然后确保所有Endpoint都具有该ENDPOINT_ADMIN角色
                .authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                // 白名单
                .mvcMatchers(redisUtils.getIgnoreUrls().toArray(new String[0])).permitAll();
        if (redisUtils.getUrls().size() != 0){
            http.authorizeRequests()
                    // 所有的资源都需要认证才能访问
                    // 以下请求需要经过oauth2鉴权,就是说以下路径只有拿到token才能访问，然后就是不是以下路径的资源都
                    // 不受oauth2鉴权，因此就算拿到token也没有访问权限
                    .antMatchers(redisUtils.getUrls().toArray(new String[0])).authenticated();
        }

    }

    /**
     * 设置资源id，和数据库当中的oauth_client_details表当中的一致
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("uaa-server");
    }
}