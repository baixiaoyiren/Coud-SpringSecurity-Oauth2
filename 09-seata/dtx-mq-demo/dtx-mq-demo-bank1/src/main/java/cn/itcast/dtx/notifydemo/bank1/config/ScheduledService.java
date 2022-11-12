package cn.itcast.dtx.notifydemo.bank1.config;

import cn.itcast.dtx.notifydemo.bank1.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import cn.itcast.dtx.notifydemo.entity.Constant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-22:57
 * Description:
 */
@Component
@Slf4j
@AllArgsConstructor
public class ScheduledService {

    private AccountChangeEventMapper accountChangeEventMapper;

    private RabbitTemplate rabbitTemplate;


    @Scheduled(cron = "0/5 0/1 * * * ?")
    public void scheduled(){
        // 查询看看哪些消息没有被消费
        log.info("=======>>>>>>使用cron============   {}",System.currentTimeMillis());
        LambdaQueryWrapper<AccountChangeEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountChangeEvent::getState, Constant.UN_STATE);
        List<AccountChangeEvent> accountChangeEvents = accountChangeEventMapper.selectList(queryWrapper);
        log.info(">>>>>>>>>>>>>定时器定时发送消息给消费者消费>>>>>>>>>>>>>>>>>>>");
        for (AccountChangeEvent accountChangeEvent : accountChangeEvents) {
            rabbitTemplate.convertAndSend(Constant.MESSAGE_EXCHANGE,Constant.ROUTING_KEY,accountChangeEvent);
        }
    }


}
