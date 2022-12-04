package com.javasm.cloud.uaa.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.uaa.entity.UserInfo;
import com.javasm.cloud.uaa.entity.vo.LoginInfoVo;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author modebing
 * @since 2022-11-05
 */
public interface IUserService extends IService<UserInfo> {

    Response login(LoginInfoVo loginInfoVo);
}
