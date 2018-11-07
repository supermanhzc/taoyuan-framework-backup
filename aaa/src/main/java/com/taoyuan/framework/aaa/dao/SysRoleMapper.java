package com.taoyuan.framework.aaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyuan.framework.aaa.entity.SysRole;
import com.taoyuan.framework.aaa.entity.UserInfo;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> selectRoleByUser(UserInfo userInfo);
}
