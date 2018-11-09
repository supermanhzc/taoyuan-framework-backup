package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.TyRoleMapper;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.common.entity.TyRole;
import com.taoyuan.framework.common.entity.TyUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TyRoleServiceImpl extends ServiceImpl<TyRoleMapper, TyRole> implements TyRoleService {
    @Override
    public List<TyRole> selectRoleByUser(TyUser tyUser) throws Exception {
        return baseMapper.selectRoleByUser(tyUser);
    }
}
