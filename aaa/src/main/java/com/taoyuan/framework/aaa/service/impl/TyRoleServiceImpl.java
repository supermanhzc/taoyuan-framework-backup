package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.TyRoleMapper;
import com.taoyuan.framework.aaa.entity.TyRole;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.TyRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TyRoleServiceImpl extends ServiceImpl<TyRoleMapper, TyRole> implements TyRoleService {
    @Override
    public List<TyRole> selectRoleByUser(UserInfo userInfo) throws Exception {
        return baseMapper.selectRoleByUser(userInfo);
    }
}
