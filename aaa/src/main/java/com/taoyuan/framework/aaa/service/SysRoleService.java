package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.aaa.entity.SysRole;
import com.taoyuan.framework.aaa.entity.UserInfo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    List<SysRole> selectRoleByUser(UserInfo userInfo) throws Exception;
}
