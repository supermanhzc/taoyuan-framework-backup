package com.taoyuan.framework.aaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyuan.framework.aaa.entity.TyPermission;
import com.taoyuan.framework.aaa.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TyPermissionMapper extends BaseMapper<TyPermission> {
    List<TyPermission> selectPermByUser(UserInfo userInfo);
}
