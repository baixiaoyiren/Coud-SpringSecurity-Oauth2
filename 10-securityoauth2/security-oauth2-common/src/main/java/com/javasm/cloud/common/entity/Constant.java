package com.javasm.cloud.common.entity;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-17:04
 * Description:
 */
public interface Constant {
    String PATH_PERMISSION = "oauth2:authentication:path&permission";
    String AUTHORITY_PREFIX = "ROLE_";
    String JWT_TOKEN_HEADER = "Bearer ";
    String TOKEN_TIME = "oauth2:authentication:bearer:expiration:";
}