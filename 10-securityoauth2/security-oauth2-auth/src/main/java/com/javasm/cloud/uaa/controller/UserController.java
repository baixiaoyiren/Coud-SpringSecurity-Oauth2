package com.javasm.cloud.uaa.controller;

import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.uaa.entity.Oauth2TokenDto;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

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
    private final TokenEndpoint tokenEndpoint;

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




    /**
     * 自定义Oauth2登录认证
     */
    @PostMapping("/oauth/token") // 注意路径不能变，否则报错无效。如果需要自定义路径，那么需要在AuthorizationServerConfigurerAdapter实现类当中修改，
    // 一旦修改指定获取token的路径之后，原有的路径将会失效
    public Response<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(Objects.requireNonNull(oAuth2AccessToken).getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return new Response<Oauth2TokenDto>(ResultCode.SUCCESS).data(oauth2TokenDto);
    }
}
