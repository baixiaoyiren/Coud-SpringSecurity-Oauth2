package com.javasm.cloud.order.controller;

import com.javasm.cloud.common.entity.Authentication;
import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-07:08
 * Description:
 */
@RestController
public class OrderController {

    @RequestMapping("/test")
    @Permission(Authentication.QUERY)
    public Response test(){
        return new Response(ResultCode.SUCCESS);
    }

    @RequestMapping("/test1")
    @Permission(Authentication.UPDATE)
    public Response test2(){
        return new Response(ResultCode.SUCCESS);
    }

    @RequestMapping("/test2")
    public Response test3(){
        return new Response(ResultCode.SUCCESS);
    }
}
