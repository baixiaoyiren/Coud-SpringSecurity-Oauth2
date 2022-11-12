package com.javasm.cloud.common.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-23:02
 * Description:
 */
@Configuration
public class MyIClientConfig{

    @Bean
    public IClientConfig iClientConfig(){
        return new DefaultClientConfigImpl();
    }
}
