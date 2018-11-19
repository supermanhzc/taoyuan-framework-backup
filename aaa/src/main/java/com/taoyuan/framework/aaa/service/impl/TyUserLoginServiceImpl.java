package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.TyUserLoginMapper;
import com.taoyuan.framework.aaa.service.TyUserLoginService;
import com.taoyuan.framework.common.entity.TyUserLoginEntity;
import org.springframework.stereotype.Service;

@Service
public class TyUserLoginServiceImpl extends ServiceImpl<TyUserLoginMapper, TyUserLoginEntity> implements TyUserLoginService {
}
