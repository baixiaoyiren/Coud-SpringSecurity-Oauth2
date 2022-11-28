package com.javasm.cloud.account.feign;

import com.javasm.cloud.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-26-11:21
 * Description:
 */
@FeignClient(value = "order",url = "http://192.168.88.1:9402")
public interface OrderFeignClient {

    @RequestMapping("/order/test1")
    public Response test2();

    @RequestMapping("/order/test")
    public Response test(@RequestParam("account") String account);
}
