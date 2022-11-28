package com.javasm.cloud.common.config;

import com.javasm.cloud.common.utils.AuthenticationRedisUtils;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-23:02
 * Description:
 */
@Configuration
public class MyIClientConfig{

    @Value("${spring.cloud.nacos.discovery.service}")
    private String applicationName;

    @Bean
    public IClientConfig iClientConfig(){
        return new DefaultClientConfigImpl();
    }

    @Bean
    public AuthenticationRedisUtils authenticationRedisUtils(){
        if (!Objects.equals(applicationName,"gateway")){
            return new AuthenticationRedisUtils();
        }else {
            return null;
        }
    }
}
