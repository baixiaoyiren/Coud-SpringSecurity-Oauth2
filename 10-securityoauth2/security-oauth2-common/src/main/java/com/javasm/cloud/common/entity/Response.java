package com.javasm.cloud.common.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-17:08
 * Description:
 */
public class Response<T> implements Serializable {
    private final ResultCode resultCode;
    private T data;

    private int code;

    private String msg;

    private static final long serialVersionUID = 9527L;

    public Response(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Response msg(String msg){
        this.msg = msg;
        resultCode.setMsg(msg);
        return this;
    }


    public Response<T> data(T data){
        this.data = data;
        return this;
    }

    public int getCode() {
        if (Objects.nonNull(resultCode)){
            return resultCode.getCode();
        }
        return 0;
    }

    public T getData() {
        return data;
    }


    public String getMsg() {
        return resultCode.getMsg();
    }
}
