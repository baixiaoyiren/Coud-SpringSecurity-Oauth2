package com.javasm.nacos.controller;

import com.javasm.domain.User;
import com.javasm.utils.JwtUtil;
import com.javasm.utils.RedisCache;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-12:46
 * @Description:
 */
@RestController
@AllArgsConstructor
public class LoginController {

   private RedisCache redisCache;

    @GetMapping("doLogin")
    public String doLogin(String name,String password){
        System.out.println(name);
        System.out.println(password);

        // 假设这里做了登录
        User user = new User();
        user.setAge(12);
        user.setName(name);
        user.setPwd(password);
        user.setId(1);

        // token
        String jwt = JwtUtil.createJWT(user.toString(), 1L, TimeUnit.MINUTES);
        redisCache.setCacheObject(jwt,user.toString(),1,TimeUnit.MINUTES);
        return jwt;
    }
}
