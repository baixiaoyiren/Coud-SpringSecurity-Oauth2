package com.javasm.cloud.uaa.entity.vo;

import com.javasm.cloud.uaa.entity.Permission;
import com.javasm.cloud.uaa.entity.Role;
import com.javasm.cloud.uaa.entity.UserInfo;

import java.util.List;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-07-16:52
 * Description:
 */
public class RequestUserInfoVO {
    private UserInfo user;
    private List<Permission> permissions;
    private Role role;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public RequestUserInfoVO(UserInfo user, List<Permission> permissions, Role role) {
        this.user = user;
        this.permissions = permissions;
        this.role = role;
    }

    public RequestUserInfoVO() {
    }
}
