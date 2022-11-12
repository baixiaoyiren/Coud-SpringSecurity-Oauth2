package com.javasm.nacos.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-23:42
 * @Description:
 */
@RestController
@AllArgsConstructor
public class ConsumerController {

    private RestTemplate restTemplate;

    @GetMapping("testRibbon")
    public String testRibbon(String serverName){
        // 正常需要拿到ip 端口 以及路径才能发起http请求
        // 但是如果被loadBalance 托管的restTemplate只能通过服务名发起请求进行负载均衡，
        // 如果是想要通过ip去请求，只能重新new一个restTemplate
        return restTemplate.getForObject("http://" + serverName + "/hello", String.class);
    }
}
