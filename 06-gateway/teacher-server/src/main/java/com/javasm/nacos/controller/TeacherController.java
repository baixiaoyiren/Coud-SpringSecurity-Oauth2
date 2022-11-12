package com.javasm.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-14:47
 * @Description:
 */
@RestController
public class TeacherController {

    @GetMapping("teach")
    public String teach(){
        return "教书学习";
    }
}
