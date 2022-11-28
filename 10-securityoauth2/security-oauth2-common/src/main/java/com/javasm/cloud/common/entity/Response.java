package com.javasm.cloud.common.entity;

import lombok.ToString;

import java.io.Serializable;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-17:08
 * Description:
 */
@ToString
public class Response<T> implements Serializable {
    private ResultCode resultCode;
    private T data;

    private int code;

    private String msg;

    private static final long serialVersionUID = 9527L;

    private Response() {
    }



    public Response(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Response msg(String msg){
        this.msg = msg;
        return this;
    }


    public Response<T> data(T data){
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }


    public String getMsg() {
        return msg;
    }
}
