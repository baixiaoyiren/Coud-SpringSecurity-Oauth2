package com.javasm.cloud.uaa.aop;

import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-12-15:38
 * Description: 把token的过期时间存入token，网关验证token是否过期
 */
@Aspect
@Component
@Slf4j
public class TokenEndpointAop {

    @Autowired
    private RedisCache redisCache;


    @Pointcut("execution(public org.springframework.http.ResponseEntity org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public void pointFn(){}


    @AfterReturning(returning = "methodResult",pointcut = "pointFn()")
    public void redisHandler(JoinPoint joinPoint, ResponseEntity<OAuth2AccessToken> methodResult) {
        // 方发的返回值
        OAuth2AccessToken accessToken = methodResult.getBody();
        // 获取到用户token的过期时间,把过期时间存入redis
        if (Objects.isNull(accessToken)){
            return;
        }
        Date expiration = accessToken.getExpiration();
        if (Objects.nonNull(expiration)){
            String token = accessToken.getValue();
            // 计算还有多少时间才过期
            Long expirationTime = expiration.getTime() - (System.currentTimeMillis());
            String key = Constant.TOKEN_TIME + token;
            // 随便一个序列化的对象存入
            redisCache.setCacheObject(key,new ArrayList<>(),expirationTime.intValue(), TimeUnit.MILLISECONDS);
        }
    }
}
