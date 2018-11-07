package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.aaa.entity.SysPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {
    List<SysPermission> selectPermByUser(UserInfo userInfo) throws Exception;
}
