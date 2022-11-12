package com.javasm.cloud.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-07-17:38
 * Description:
 */
@TableName("auth_user")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 户主姓名
     */

    private String username;

    /**
     * 密码
     */

    private String password;

    /**
     * 账户是否可用
     */
    private int enabled;
}
