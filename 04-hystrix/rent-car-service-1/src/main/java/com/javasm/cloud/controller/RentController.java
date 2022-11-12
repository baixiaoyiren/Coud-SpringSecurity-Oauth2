package com.javasm.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-05:58
 * @Description:
 */
@RestController
public class RentController {

    @GetMapping("rent")
    public String rent(){
        return "租车成功";
    }
}
