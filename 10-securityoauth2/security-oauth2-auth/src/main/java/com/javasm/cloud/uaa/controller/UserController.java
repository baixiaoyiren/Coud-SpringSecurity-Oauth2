package com.javasm.cloud.uaa.controller;

import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.uaa.entity.UserInfo;
import com.javasm.cloud.uaa.entity.vo.OAuthLoginInfoVo;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
public class UserController{

    @Resource
    private IUserService userService;

    @Value("${auth.open_id}")
    private String id;

    @Value("${auth.open_secret}")
    private String secret;

    @Value("${auth.open_grant_type}")
    private String grantType;


    @PostMapping("/user/login")
    // 本系统内部指定登录入口
    public Response innerLogin(@RequestBody UserInfo userInfo){
        OAuthLoginInfoVo oAuthLoginInfoVo = new OAuthLoginInfoVo();
        oAuthLoginInfoVo.setUserName(userInfo.getUsername());
        oAuthLoginInfoVo.setPassword(userInfo.getPassword());
        oAuthLoginInfoVo.setId(id);
        oAuthLoginInfoVo.setSecret(secret);
        oAuthLoginInfoVo.setGrantType(grantType);
        return userService.login(oAuthLoginInfoVo);
    }



    @GetMapping("/getUserInfo")
    @Permission
    public Response getUserInfo(Authentication authentication, HttpServletRequest request){
        // 获取到用户的信息
        String user = request.getHeader("user");
        System.out.println(user);
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
