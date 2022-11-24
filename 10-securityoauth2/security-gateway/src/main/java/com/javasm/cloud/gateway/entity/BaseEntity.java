package com.javasm.cloud.gateway.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-07-16:24
 * Description:
 */
@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;
}
