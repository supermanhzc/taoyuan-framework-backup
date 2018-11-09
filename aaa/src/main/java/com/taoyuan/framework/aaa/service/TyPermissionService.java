package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.aaa.entity.TyPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;

import java.util.List;

public interface TyPermissionService extends IService<TyPermission> {
    List<TyPermission> selectPermByUser(UserInfo userInfo) throws Exception;
}
