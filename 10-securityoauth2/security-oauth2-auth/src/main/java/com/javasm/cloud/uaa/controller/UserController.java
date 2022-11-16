package com.javasm.cloud.uaa.controller;

import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 *
 * @author modebing
 * @since 2022-11-05
 */
@RestController
@AllArgsConstructor
public class UserController {

    private UserServiceImpl userService;


    @GetMapping("/getUserInfo")
    @Permission
    public Response getUserInfo(Authentication authentication){
        return new Response(ResultCode.SUCCESS).data(authentication);
    }

    @GetMapping("/getUser")
    @Permission
    public Response getUser(){
        return new Response(ResultCode.SUCCESS).msg("encrypt");
    }

    @PostMapping("/addInfo")
    public Response addUserInfo(@RequestBody RequestUserInfoVO vo){
        userService.setUserInfo(vo);
        return new Response(ResultCode.SUCCESS);
    }

    // 退出登录
    @PostMapping("/user/logout")
    @Permission
    public Response logout(Authentication authentication){
        return userService.logout(authentication);
    }
}
