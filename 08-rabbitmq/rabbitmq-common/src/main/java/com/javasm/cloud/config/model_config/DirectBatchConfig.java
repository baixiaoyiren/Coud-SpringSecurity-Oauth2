package com.javasm.cloud.config.model_config;

import com.javasm.cloud.entity.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-14-00:21
 * @Description:
 * 路由模式实现批量消费
 */
@Configuration
public class DirectBatchConfig {


    /**
     * 路由模式
     *
     * @return
     */
    @Bean
    public Queue batchQueue1(){
        return new Queue(Constant.BATCH_ROUTE_QUEUE_1);
    }

    @Bean
    public Queue batchQueue2(){
        return new Queue(Constant.BATCH_ROUTE_QUEUE_2);
    }


    @Bean
    public Queue batchQueue3(){
        return new Queue(Constant.BATCH_ROUTE_QUEUE_3);
    }


    /**
     * 路由交换机
     */
    @Bean
    public DirectExchange routeBatchExchange(){
        return ExchangeBuilder.directExchange(Constant.BATCH_ROUTE_EXCHANGE).durable(true).build();
    }


    // 绑定交换机和队列
    @Bean
    public Binding routeBatchBinding1(){
        return BindingBuilder.bind(batchQueue1()).to(routeBatchExchange()).with(Constant.BATCH_ROUTE_KEY);
    }

    // 绑定交换机和队列
    @Bean
    public Binding routeBatchBinding2(){
        return BindingBuilder.bind(batchQueue2()).to(routeBatchExchange()).with(Constant.BATCH_ROUTE_KEY);
    }

    // 绑定交换机和队列
    @Bean
    public Binding routeBatchBinding3(){
        return BindingBuilder.bind(batchQueue3()).to(routeBatchExchange()).with(Constant.BATCH_ROUTE_KEY);
    }
}
