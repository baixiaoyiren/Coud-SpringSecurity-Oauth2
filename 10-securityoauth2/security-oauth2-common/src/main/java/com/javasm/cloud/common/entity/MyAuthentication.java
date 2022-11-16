package com.javasm.cloud.common.entity;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-16:50
 * Description: 权限信息
 */
public enum MyAuthentication {

    INSERT("insert","新增权限"),
    QUERY("query","查询权限"),
    UPDATE("update","更新权限"),
    DELETE("delete","删除权限");

    private String value;

    private String desc;

    MyAuthentication(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
