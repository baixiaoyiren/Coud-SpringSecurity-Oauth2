package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-15:56
 * @Description: 简单模式 ，不用绑定交换机
 */
@Configuration
public class SimpleMqConfig {
    @Bean
    public Queue simpleQueue(){
        return new Queue(Constant.SIMPLE_MODEL);
    }
}
