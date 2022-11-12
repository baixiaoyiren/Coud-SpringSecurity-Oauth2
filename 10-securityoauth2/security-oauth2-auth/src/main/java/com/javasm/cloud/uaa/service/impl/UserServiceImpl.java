package com.javasm.cloud.uaa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.exception.MyAuthenticationException;
import com.javasm.cloud.uaa.entity.*;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.mapper.*;
import com.javasm.cloud.uaa.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService, UserDetailsService {

    private PermissionMapper permissionMapper;

    private RoleMapper roleMapper;

    private RolePermissionMapper rolePermissionMapper;

    private UserMapper userMapper;

    private UserRoleMapper userRoleMapper;

    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        /*
        此处的逻辑是：
        1。查询数据库判断用户名是否存在，如果不存在则抛出UsernameNotFoundException异常
        2。把查询出来的密码进行解析，或者把密码放入构造方法当中
        */
       //if ("admin".equals(username)){
       //    // 模拟数据库当中查询出来的密码
       //    String password = passwordEncoder.encode("123456");
       //    // 数据库当中查询出来的权限:admin mormal
       //    List<Permission> permissions = Arrays.asList(new Permission("admin"), new Permission("normal"));
       //    // 构建springsecurity的user对象并返回
       //
       //    // Long id, String username, String password, int enabled, Date createTime, Date updateTime, List<Permission> permission
       //    AuthUser auser = new AuthUser(1L, username, password, 1, new Date(), new Date(), permissions);
       //    log.info(auser.toString());
       //    return auser;
       //}

        // 查询出用户信息
        // 构建Querywarraper对象
        LambdaQueryWrapper<UserInfo> authUserQueryWrapper = new LambdaQueryWrapper<>();
        authUserQueryWrapper.eq(UserInfo::getUsername,username);
        UserInfo authUser = userMapper.selectOne(authUserQueryWrapper);
        if (Objects.isNull(authUser)){
            throw new MyAuthenticationException("用户名错误");
        }
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(authUser,user);
        // 查询角色id
        LambdaQueryWrapper<UserRole> userRoleMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleMapperLambdaQueryWrapper.eq(UserRole::getUserId,authUser.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleMapperLambdaQueryWrapper);
        // 角色id
        List<Integer> roleId = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 查询用户的权限
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionLambdaQueryWrapper.in(RolePermission::getRoleId,roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionLambdaQueryWrapper);
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionLambdaQueryWrapper.in(Permission::getId,permissionIds);
        List<Permission> permissions = permissionMapper.selectList(permissionLambdaQueryWrapper);
        // 封装到user当中
        user.setPermissions(permissions);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    // 添加用户和权限和角色
    public Response setUserInfo(RequestUserInfoVO info){
        UserInfo authUser = info.getUser();
        List<Permission> permissions = info.getPermissions();
        Role role = info.getRole();
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        // 添加用户
        userMapper.insert(authUser);
        // 添加角色
        roleMapper.insert(role);
        // 添加权限
        permissions.forEach(item->{
                permissionMapper.insert(item);
            // 添加角色权限
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(item.getId());
            rolePermissionMapper.insert(rolePermission);
            }
        );

        // 添加用户角色
        UserRole userRole = new UserRole();
        userRole.setUserId(Integer.valueOf(authUser.getId().toString()));
        userRole.setRoleId(role.getId());
        userRoleMapper.insert(userRole);

        return new Response(ResultCode.SUCCESS);

    }


}
