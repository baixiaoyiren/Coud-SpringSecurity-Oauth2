package com.javasm.cloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-11-12:33
 * @Description:
 */
@Data
public class User {
    private String name;

    private Integer age;

    private BigDecimal money;
}
