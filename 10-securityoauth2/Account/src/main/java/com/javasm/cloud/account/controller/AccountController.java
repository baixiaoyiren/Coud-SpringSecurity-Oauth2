package com.javasm.cloud.account.controller;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.javasm.cloud.account.feign.OrderFeignClient;
import com.javasm.cloud.common.entity.MyAuthentication;
import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-02:40
 * Description:
 */
@RestController
@AllArgsConstructor
@Slf4j
public class AccountController {

    private OrderFeignClient feignClient;

    private NacosServiceDiscovery nacosDiscoveryClient;

    @RequestMapping("/test")
    @Permission(MyAuthentication.DELETE)
    public Response test(){
        return new Response(ResultCode.SUCCESS).msg("删除成功！");
    }

    @RequestMapping("/test1")
    @Permission
    public Response test1() throws NacosException {
        //List<ServiceInstance> instances = nacosDiscoveryClient.getInstances("order");
        //URI uri = instances.get(0).getUri();
        //log.info(uri.getPath());
        return new Response(ResultCode.SUCCESS).msg("查询成功！").data(feignClient.test2());
    }

    @RequestMapping("/test2")
    public Response test2(){

        return new Response(ResultCode.SUCCESS).msg("没有添加任何权限，任何人都可以访问！").data(feignClient.test("123.0").getData());
    }
}

