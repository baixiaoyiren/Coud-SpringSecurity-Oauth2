package com.javasm.feign;

import com.javasm.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-00:36
 * @Description:
 */
@FeignClient(value = "order-server") // 需要写服务提供者的应用名称
public interface UserOrderFeign {

    /**
     * feign的默认等待时间是1秒，超过1秒则为超时.直接报错
     *
     * @return
     */
    @GetMapping("doOrder")
    String doOrder();


    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable("name") String name, @PathVariable("age") int age);

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = false,value = "name") String name);


    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false,value = "name") String name, @RequestParam(required = false,value = "age") int age);


    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order);

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order, @RequestParam("name") String name);

    @GetMapping("testTime")
    public String testTime(@RequestParam("date") Date date);
}
