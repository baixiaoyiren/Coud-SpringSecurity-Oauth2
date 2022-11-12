package com.javasm.cloud.controller;

import com.javasm.cloud.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-22:59
 * @Description:
 */
@RestController
@Slf4j
public class TestController {


    @GetMapping("testGet")
    public String get(String name) {
        System.out.println(name);
        return "ok";
    }


    /**
     * json提交
     *
     * @param user
     * @return
     */
    @PostMapping("testPost1")
    public User post1(@RequestBody User user) {
        log.info(user.toString());
        return user;
    }


    /**
     * 表单提交
     *
     * @param user
     * @return
     */
    @PostMapping("testPost2")
    public User post2(User user) {
        log.info(user.toString());
        return user;
    }


}
