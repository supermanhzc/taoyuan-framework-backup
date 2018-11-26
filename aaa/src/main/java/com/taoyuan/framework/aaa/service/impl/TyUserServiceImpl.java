package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.UserInfoMapper;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.common.entity.TyUser;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

@Service
public class TyUserServiceImpl extends ServiceImpl<UserInfoMapper, TyUser> implements TyUserService {


    @Override
    public TyUser getUserByName(String name) {
        QueryWrapper<TyUser> wrapper = new QueryWrapper<TyUser>();
        wrapper.lambda().eq(TyUser::getName, name);
        return getOne(wrapper);
    }

    @Override
    public TyUser getUserById(Long id) {
        QueryWrapper<TyUser> wrapper = new QueryWrapper<TyUser>();
        wrapper.lambda().eq(TyUser::getId, id);
        return getOne(wrapper);
    }
}
