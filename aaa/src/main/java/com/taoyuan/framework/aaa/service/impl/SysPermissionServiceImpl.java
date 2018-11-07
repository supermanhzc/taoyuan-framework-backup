package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.SysPermissionMapper;
import com.taoyuan.framework.aaa.entity.SysPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.SysPermissionService;
import com.taoyuan.framework.aaa.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Override
    public List<SysPermission> selectPermByUser(UserInfo userInfo) throws Exception {
        return baseMapper.selectPermByUser(userInfo);
    }
}
