package com.taoyuan.framework.bs.mapper.operlog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyuan.framework.bs.entity.operlog.TyOperLog;
import com.taoyuan.framework.bs.mapper.TyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TyOperLogMapper extends TyBaseMapper<TyOperLog> {
}
