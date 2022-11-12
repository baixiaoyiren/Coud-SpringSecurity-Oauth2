package com.javasm.cloud.common.entity;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-17:09
 * Description:
 */
public enum ResultCode {

    SUCCESS(200,"成功"),
    ERROR(404,"请求丢失"),
    BADREQUEST(400,"错误请求"),
    UNAUTHORIZED(401,"认证不成功,token失效或丢失"),
    EXPIRED(402,"认证不成功,token失效"),
    FORBIDDEN(403,"没有权限，禁止访问");



    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
