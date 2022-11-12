package com.javasm.cloud.uaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.cloud.uaa.entity.UserRole;
import com.javasm.cloud.uaa.mapper.UserRoleMapper;
import com.javasm.cloud.uaa.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色信息 服务实现类
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
