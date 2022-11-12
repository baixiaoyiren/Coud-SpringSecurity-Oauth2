package cn.itcast.dtx.notifydemo.bank1.service.impl;

import cn.itcast.dtx.notifydemo.bank1.dao.AccountChangeEventMapper;
import cn.itcast.dtx.notifydemo.bank1.service.AccountChangeEventService;
import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-20:15
 * Description:
 */
@Service
@AllArgsConstructor
public class AccountChangeEventServiceImpl implements AccountChangeEventService {
    private AccountChangeEventMapper accountChangeEventMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateState(AccountChangeEvent accountChangeEvent) {
        LambdaUpdateWrapper<AccountChangeEvent> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(AccountChangeEvent::getTxNo,accountChangeEvent.getTxNo());
        return accountChangeEventMapper.update(accountChangeEvent,queryWrapper);

    }
}
