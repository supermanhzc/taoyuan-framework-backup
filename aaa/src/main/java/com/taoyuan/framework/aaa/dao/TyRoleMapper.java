package com.taoyuan.framework.aaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyuan.framework.aaa.entity.TyRole;
import com.taoyuan.framework.aaa.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TyRoleMapper extends BaseMapper<TyRole> {
    List<TyRole> selectRoleByUser(UserInfo userInfo);
}
