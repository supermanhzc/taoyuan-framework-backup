package com.taoyuan.framework.aaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyuan.framework.aaa.entity.SysPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> selectPermByUser(UserInfo userInfo);
}
