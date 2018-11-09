package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.TyPermissionMapper;
import com.taoyuan.framework.aaa.entity.TyPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TyPermissionServiceImpl extends ServiceImpl<TyPermissionMapper, TyPermission> implements TyPermissionService {
    @Override
    public List<TyPermission> selectPermByUser(UserInfo userInfo) throws Exception {
        return baseMapper.selectPermByUser(userInfo);
    }
}
