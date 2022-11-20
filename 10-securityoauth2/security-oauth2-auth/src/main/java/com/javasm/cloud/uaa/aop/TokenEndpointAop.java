package com.javasm.cloud.uaa.aop;

import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-19-04:04
 * Description: 获取token自定义返回结果
 */
@Component
@Aspect
@Slf4j
public class TokenEndpointAop {

    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public void method(){};

    @SneakyThrows
    @Around(value = "method()")
    public ResponseEntity doSomeThing(ProceedingJoinPoint joinPoint){
        ResponseEntity<OAuth2AccessToken> result = (ResponseEntity<OAuth2AccessToken>) joinPoint.proceed();

        OAuth2AccessToken accessToken = result.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        Response data = new Response<>(ResultCode.SUCCESS).data(accessToken).msg("获取token成功！");
        return new ResponseEntity(data, headers, HttpStatus.OK);
    }
}
