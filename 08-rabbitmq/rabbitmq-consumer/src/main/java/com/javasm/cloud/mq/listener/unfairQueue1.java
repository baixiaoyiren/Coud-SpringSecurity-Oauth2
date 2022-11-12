package com.javasm.cloud.mq.listener;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-22:03
 * @Description: rabbitmq 不公平分发
 * rabbitmq 默认是公平的，处理快和处理慢都是一样处理数量
 * 实现不公平的分发，就处理消息快的就让它多处理
 * 慢的就少处理
 */
//@Component
//@RabbitListener(queues = "unfair_queue")
@Slf4j
public class unfairQueue1 {


    @RabbitHandler
    public void recevieMsg(String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws InterruptedException {
        Thread.sleep(5000);
        log.error("*************{}************",message);
        //现在改为设置成手动ack应答（channel.basicAck方法），
        //这样做的目的是保证消息在正确消费后给回馈，说明我正确消费了。这时队列就可以把这条消息删除了，
        //如果消费端接收了消息，但是没有给返回ack应答，那么这条消息会继续存在unacked状态下，占据队列的空间，
        //等到空间满了，就会出现接下来的消息不能被消费的情况。

        try {
            channel.basicAck(3,true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // long deliveryTag 消息接收tag boolean multiple 是否批量确认
        System.out.println("deliveryTag="+deliveryTag);


        /**
         * 无异常就确认消息
         * basicAck(long deliveryTag, boolean multiple)
         * deliveryTag:取出来当前消息在队列中的的索引;
         * multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
         * deliveryTag为5及其以下的消息;一般设置为false
         */

        if(deliveryTag==5){
            try {
                channel.basicAck(deliveryTag,true);
            } catch (IOException e) {
                e.printStackTrace();
                /**
                 * 有异常就绝收消息
                 * basicNack(long deliveryTag, boolean multiple, boolean requeue)
                 * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
                 *         false:将消息丢弃
                 */
                try {

                    channel.basicNack(deliveryTag,false,true);
                    // long deliveryTag, boolean requeue
                    // channel.basicReject(deliveryTag,true);

                    Thread.sleep(1000);     // 这里只是便于出现死循环时查看

                    /*
                     * 一般实际异常情况下的处理过程：记录出现异常的业务数据，将它单独插入到一个单独的模块，
                     * 然后尝试3次，如果还是处理失败的话，就进行人工介入处理
                     */

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
