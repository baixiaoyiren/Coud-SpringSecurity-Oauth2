package com.javasm.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-17:59
 * @Description:
 */
@RestController
@RefreshScope // 给这个类上加一个刷新的作用域
public class TestController {
    @Value("${hero.name}")
    private String name;

    @Value("${hero.age}")
    private Integer age;


    @RequestMapping("hero")
    public Map<String,Object> info(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);
        return map;
    }

}
