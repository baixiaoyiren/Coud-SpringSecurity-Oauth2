package com.javasm.nacos.controller;


import com.javasm.entity.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-04:31
 * @Description:
 *
 *
 * url 路径传参
 *
 * get 传递一个参数
 * get传递多个参数
 *
 *
 * post传递一个对象+一个基本参数
 * post传递多个对象
 */
@RestController
public class ParamsController {

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable("name")String name,@PathVariable("age")int age){
        System.out.println(name+":"+age);
        return "ok";
    }


    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = false) String name){
        System.out.println(name);
        return "ok";
    }


    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false) String name,@RequestParam(required = false) int age){
        System.out.println(name+":"+age);
        return "ok";
    }


    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order){
        System.out.println(order);
        return "ok";
    }

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam("name") String name){
        System.out.println(order);
        System.out.println(name);
        return "ok";
    }


    @GetMapping("testTime")
    public String testTime(@RequestParam Date date){
        System.out.println(date);
        return "ok";
    }
}
