package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.SysRoleMapper;
import com.taoyuan.framework.aaa.entity.SysRole;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public List<SysRole> selectRoleByUser(UserInfo userInfo) throws Exception {
        return baseMapper.selectRoleByUser(userInfo);
    }
}
