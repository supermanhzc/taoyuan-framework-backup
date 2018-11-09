package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.common.entity.TyRole;
import com.taoyuan.framework.common.entity.TyUser;

import java.util.List;

public interface TyRoleService extends IService<TyRole> {
    List<TyRole> selectRoleByUser(TyUser tyUser) throws Exception;
}
