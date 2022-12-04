package com.javasm.cloud.uaa.entity.vo;

import com.javasm.cloud.uaa.entity.UserInfo;
import lombok.Data;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-12-04-10:02
 * Description:
 */
@Data
public class LoginInfoVo {
    private UserInfo userInfo;

    private String secret;

    private String id;

    private String grantType;
}
