package com.javasm.cloud.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("auth_permission")
public class Permission extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;


    public Permission(String name) {
        this.name = name;
    }

    public Permission() {
    }
}
