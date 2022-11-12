package com.javasm.cloud.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:27
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class Response <T>{
    private Integer code;

    private String msg;

    private T data;

    public Response<T> code(HttpStatus status){
        code = status.value();
        return this;
    }

    public Response<T> msg(String msg){
        this.msg = msg;
        return this;
    }

    public Response<T> data(T data){
        this.data = data;
        return this;
    }
}
