package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-16:12
 * @Description: 工作模式
 */
@Configuration
public class WorkMqConfig {


    @Bean
    public Queue workQueue() {
        return new Queue(Constant.WORK_QUEUE);
    }

}
