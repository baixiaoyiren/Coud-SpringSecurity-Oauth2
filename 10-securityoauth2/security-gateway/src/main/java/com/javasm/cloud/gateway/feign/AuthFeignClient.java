package com.javasm.cloud.gateway.feign;

import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.gateway.config.MyFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-03:44
 * Description:
 */
@FeignClient(value = "auth",configuration = MyFeignClient.class)
public interface AuthFeignClient {

    // 判断是否是刷新token
    @RequestMapping("/auth/user/isRefreshToken")
    public Response convertAccessToken(@RequestParam("token") String token);

}
