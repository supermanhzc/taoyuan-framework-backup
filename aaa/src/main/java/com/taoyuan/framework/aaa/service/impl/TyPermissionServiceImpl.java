package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.TyPermissionMapper;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.common.entity.TyPermission;
import com.taoyuan.framework.common.entity.TyUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TyPermissionServiceImpl extends ServiceImpl<TyPermissionMapper, TyPermission> implements TyPermissionService {
    @Override
    public List<TyPermission> selectPermByUser(Long userId) throws Exception {
        return baseMapper.selectPermByUser(userId, "action");
    }

    @Override
    public List<TyPermission> selectMenuByUser(Long userId) throws Exception {
        return baseMapper.selectPermByUser(userId, "menu");
    }
}
