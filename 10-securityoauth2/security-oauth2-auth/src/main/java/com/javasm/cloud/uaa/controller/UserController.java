package com.javasm.cloud.uaa.controller;

import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.uaa.entity.UserInfo;
import com.javasm.cloud.uaa.entity.vo.OAuthLoginInfoVo;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.*;

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
    private UserServiceImpl userService;
    @Resource
    private JwtTokenStore jwtTokenStore;

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

    // 判断是否是刷新token
    @RequestMapping("/user/isRefreshToken")
    public Response convertAccessToken(String token) {
        Response response = null;
        try {
            OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(token);
            response = new Response<>(ResultCode.SUCCESS).data(oAuth2AccessToken).msg("获取token成功！");
        } catch (Throwable e) {
            response = new Response(ResultCode.BADREQUEST).msg(e.getMessage());
        }
        return response;
    }

}
