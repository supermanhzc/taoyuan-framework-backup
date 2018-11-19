package com.taoyuan.framework.aaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyuan.framework.common.entity.TyUser;

public interface TyUserService extends IService<TyUser> {

    TyUser getUserByName(String name);
}
