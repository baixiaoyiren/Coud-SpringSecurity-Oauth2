package com.javasm.cloud.feign;

import com.javasm.cloud.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:31
 * @Description:
 */
@FeignClient(value = "user-server")
public interface UserServiceFeign {


    @GetMapping("userInfo")
    Response getUserInfo(@RequestParam(required = false, value = "userName") String userName);

}
