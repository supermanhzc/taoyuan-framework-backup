package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.common.entity.TyPermission;
import com.taoyuan.framework.common.entity.TyUser;

import java.util.List;

public interface TyPermissionService extends IService<TyPermission> {
    List<TyPermission> selectPermByUser(TyUser tyUser) throws Exception;
}
