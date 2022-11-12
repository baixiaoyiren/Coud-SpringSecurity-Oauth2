package com.javasm.cloud.controller;

import com.javasm.cloud.feign.RentCarServiceFeign;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-06:00
 * @Description:
 */
@RestController
@AllArgsConstructor
public class CustomerController {

    private RentCarServiceFeign feign;

    @GetMapping("customer")
    public String customerRent(){
        return feign.rent();
    }
}
