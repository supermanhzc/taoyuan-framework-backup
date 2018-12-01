package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.common.entity.TyUser;
import com.taoyuan.framework.common.http.TyResponse;

public interface TyUserService extends IService<TyUser> {

    TyResponse register(TyUser userInfo);

    TyResponse modify(TyUser userInfo);

    TyResponse delete(Long id);

    TyUser getUserByName(String name);

    TyUser getUserById(Long id);
}
