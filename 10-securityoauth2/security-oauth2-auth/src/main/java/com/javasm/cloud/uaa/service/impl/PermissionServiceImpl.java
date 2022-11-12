package com.javasm.cloud.uaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.cloud.uaa.entity.Permission;
import com.javasm.cloud.uaa.mapper.PermissionMapper;
import com.javasm.cloud.uaa.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限信息 服务实现类
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
