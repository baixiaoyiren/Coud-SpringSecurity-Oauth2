package com.javasm.cloud.uaa.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import com.javasm.cloud.common.exception.CommonException;
import com.javasm.cloud.common.exception.MyAuthenticationException;
import com.javasm.cloud.common.utils.RedisCache;
import com.javasm.cloud.uaa.entity.*;
import com.javasm.cloud.uaa.entity.vo.OAuthLoginInfoVo;
import com.javasm.cloud.uaa.entity.vo.RequestUserInfoVO;
import com.javasm.cloud.uaa.mapper.*;
import com.javasm.cloud.uaa.service.IUserService;
import com.nimbusds.jose.JWSObject;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    private RedisCache redisCache;

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
        authUserQueryWrapper.eq(UserInfo::getUsername, username);
        UserInfo authUser = userMapper.selectOne(authUserQueryWrapper);
        if (Objects.isNull(authUser)) {
            throw new MyAuthenticationException("用户名错误");
        }
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(authUser, user);
        // 查询角色id
        LambdaQueryWrapper<UserRole> userRoleMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleMapperLambdaQueryWrapper.eq(UserRole::getUserId, authUser.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleMapperLambdaQueryWrapper);
        // 角色id
        List<Integer> roleId = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 查询用户的权限
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionLambdaQueryWrapper.in(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionLambdaQueryWrapper);
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionLambdaQueryWrapper.in(Permission::getId, permissionIds);
        List<Permission> permissions = permissionMapper.selectList(permissionLambdaQueryWrapper);
        // 封装到user当中
        user.setPermissions(permissions);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 添加用户和权限和角色
    public void setUserInfo(RequestUserInfoVO info) {
        UserInfo authUser = info.getUser();
        List<Permission> permissions = info.getPermissions();
        Role role = info.getRole();
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        // 添加用户
        userMapper.insert(authUser);
        // 添加角色
        roleMapper.insert(role);
        // 添加权限
        permissions.forEach(item -> {
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

    }


    @Override
    @SneakyThrows
    public Response logout(Authentication authentication) {
        // 要实现退出逻辑方案：
        // 白名单方案：登录成功，设置token到redis当中，每次都需要校验redis中是否包含该token；退出成功后，删除redis中的token值
        // 黑名单方案：退出成功，往redis存储相关的token信息，每次请求资源之前都需要校验token是否存在于redis当中，存在的话就说明之前退出登录了，token无效
        if (Objects.nonNull(authentication)){
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            String token = details.getTokenValue();
            if (!StringUtils.isEmpty(token)) {
                JWSObject jwsObject = JWSObject.parse(token);
                String userStr = jwsObject.getPayload().toString();
                JSONObject object = JSONObject.parseObject(userStr);
                // 获取到过期时间
                // 多久过期
                Long expDate = object.getLong("exp"); // 秒为单位，从1970年开始
                long expireTome = expDate*1000L - System.currentTimeMillis();
                redisCache.setCacheObject(Constant.TOKEN_TIME+token,-1,Integer.parseInt(Long.toString(expireTome)), TimeUnit.MILLISECONDS);
            }
        }
        return new Response(ResultCode.SUCCESS).msg("退出登录成功");
    }

    // 远程调用接口进行登录
    @SneakyThrows
    @Override
    public Response login(OAuthLoginInfoVo oAuthLoginInfoVo) {
        // 创建post请求
        String url = "http://localhost/auth/oauth/token";
        List<BasicNameValuePair> body= new ArrayList<>();
        body.add(new BasicNameValuePair("grant_type", oAuthLoginInfoVo.getGrantType()));
        body.add(new BasicNameValuePair("username", oAuthLoginInfoVo.getUserName()));
        body.add(new BasicNameValuePair("password", oAuthLoginInfoVo.getPassword()));
        body.add(new BasicNameValuePair("client_id", oAuthLoginInfoVo.getId()));
        body.add(new BasicNameValuePair("client_secret", oAuthLoginInfoVo.getSecret()));
        String responseStr = null;
        try {
            responseStr = Request.Post(url).bodyForm(body).execute().returnContent().asString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CommonException("登录信息错误"+e.getMessage());
        }
        JSONObject jsonObject = JSONObject.parseObject(responseStr, JSONObject.class);
        // 返回的data是JsonObject对象
        return new Response(ResultCode.SUCCESS).data(jsonObject.get("data"));
    }
}
