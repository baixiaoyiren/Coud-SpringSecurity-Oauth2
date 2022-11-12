package com.javasm.cloud.entity;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-24-00:24
 * Description:
 */

public abstract class MyFunction<T,R> implements HandleBusiness<T>{
    private R data;

    public MyFunction(R data) {
        this.data = data;
    }

    private MyFunction() {
    }
}
