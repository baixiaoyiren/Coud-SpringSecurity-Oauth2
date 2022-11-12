package com.javasm.cloud.uaa.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.cloud.uaa.entity.Role;
import com.javasm.cloud.uaa.mapper.RoleMapper;
import com.javasm.cloud.uaa.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
