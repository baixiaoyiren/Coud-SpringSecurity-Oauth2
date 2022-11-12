package com.javasm.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-23:24
 * @Description:
 */
@RestController
public class ProviderController {

    @GetMapping("hello")
    public String hello(){
        return "我是提供者B";
    }
}
