package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.common.entity.TyUser;
import com.taoyuan.framework.common.http.TyResponse;

public interface TyUserService extends IService<TyUser> {

    boolean register(TyUser userInfo);

    boolean modify(TyUser userInfo);

    boolean delete(Long id);

    TyUser getUserByName(String name);

    TyUser getUserById(Long id);
}
