package com.javasm.cloud.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@EqualsAndHashCode(callSuper = false)
@ToString
public class AuthUser extends BaseEntity implements Serializable, UserDetails {

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


    @JsonIgnore
    private Collection<SimpleGrantedAuthority> permissions;

    public AuthUser() {
    }

    public AuthUser(Long id, String username, String password, int enabled, Date createTime,Date updateTime, List<Permission> permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.permissions = new ArrayList<>();
        this.permissions = permissions.stream().map(Permission::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled == 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }
    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }
    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Collection<SimpleGrantedAuthority> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions.stream().map(Permission::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
