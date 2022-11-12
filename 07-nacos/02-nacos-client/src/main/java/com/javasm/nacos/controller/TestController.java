package com.javasm.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:27
 * @Description:
 */
@RestController
public class TestController {
    @GetMapping("info")
    public String info(){
        return "年薪20w";
    }
}
