package com.javasm.cloud.account.controller;

import com.javasm.cloud.common.entity.MyAuthentication;
import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-02:40
 * Description:
 */
@RestController
public class AccountController {

    @RequestMapping("/test")
    @Permission(MyAuthentication.DELETE)
    public Response test(){
        return new Response(ResultCode.SUCCESS).msg("删除成功！");
    }

    @RequestMapping("/test1")
    @Permission
    public Response test1(){
        return new Response(ResultCode.SUCCESS).msg("查询成功！");
    }

    @RequestMapping("/test2")
    public Response test2(){
        return new Response(ResultCode.SUCCESS).msg("没有添加任何权限，任何人都可以访问！");
    }
}

