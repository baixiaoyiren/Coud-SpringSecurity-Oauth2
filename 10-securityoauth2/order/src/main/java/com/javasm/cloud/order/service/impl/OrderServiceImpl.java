package com.javasm.cloud.order.service.impl;

import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-21-09:23
 * Description:
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    //private TransactionTemplate transactionTemplate;

    // 编程式事务
    @Override
    @Trace //加了这个注解 ，skywalk也会将业务方法加入 到监控当中
    @Tags({
            @Tag(key = "getOrder",value = "returnedObj"), // key是方法名，returnedObj返回值.固定写法，就写returnedObj
            @Tag(key = "getOrder",value = "arg[0]") // key是方法名，arg[0]是方法参数
    })
    public Response getOrder(String account) {
        //transactionTemplate.execute(item->{
        //    return null;
        //});


        return new Response(ResultCode.SUCCESS).data("aa");
    }
}
