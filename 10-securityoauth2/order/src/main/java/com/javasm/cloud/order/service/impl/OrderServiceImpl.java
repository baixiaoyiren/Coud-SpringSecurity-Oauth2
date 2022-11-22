package com.javasm.cloud.order.service.impl;

import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-21-09:23
 * Description:
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private TransactionTemplate transactionTemplate;

    // 编程式事务
    @Override
    public Response getOrder() {
        transactionTemplate.execute(item->{
            return null;
        });

        return null;
    }
}
