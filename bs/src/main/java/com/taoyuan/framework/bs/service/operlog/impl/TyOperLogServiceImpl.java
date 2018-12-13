package com.taoyuan.framework.bs.service.operlog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.bs.mapper.operlog.TyOperLogMapper;
import com.taoyuan.framework.bs.entity.operlog.TyOperLog;
import com.taoyuan.framework.bs.service.TyBaseServiceImpl;
import com.taoyuan.framework.bs.service.operlog.TyOperLogService;
import org.springframework.stereotype.Service;

@Service
public class TyOperLogServiceImpl extends TyBaseServiceImpl<TyOperLogMapper, TyOperLog> implements TyOperLogService {
}