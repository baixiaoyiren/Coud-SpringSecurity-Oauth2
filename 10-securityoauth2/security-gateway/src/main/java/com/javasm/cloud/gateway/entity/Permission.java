package com.javasm.cloud.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 权限信息
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;


    public Permission(String name) {
        this.name = name;
    }

    public Permission() {
    }
}
