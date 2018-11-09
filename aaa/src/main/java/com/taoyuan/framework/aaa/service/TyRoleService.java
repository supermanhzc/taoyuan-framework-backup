package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.aaa.entity.TyRole;
import com.taoyuan.framework.aaa.entity.UserInfo;

import java.util.List;

public interface TyRoleService extends IService<TyRole> {
    List<TyRole> selectRoleByUser(UserInfo userInfo) throws Exception;
}
