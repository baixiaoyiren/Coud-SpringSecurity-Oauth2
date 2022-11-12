package com.javasm.cloud.controller.impl;

import com.javasm.cloud.entity.Response;
import com.javasm.cloud.entity.User;
import com.javasm.cloud.feign.UserServiceFeign;
import com.javasm.cloud.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.Executor;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-13-12:55
 * @Description:
 */
@RestController

public class UserServerFeignImpl implements UserServiceFeign {

    ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();



    @Autowired
    private RedisUtil redisUtils;

    @Autowired
    @Qualifier("async")
    private Executor executor;

    @Override
    public Response getUserInfo(String userName) {
        User user = new User();
        user.setName(userName);
        user.setAge(12);
        user.setMoney(new BigDecimal("123.11"));
        return new Response().msg("发送成功").data(user).code(HttpStatus.OK);
    }




}
