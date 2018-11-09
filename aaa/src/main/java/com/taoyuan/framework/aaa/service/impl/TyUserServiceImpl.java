package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.UserInfoMapper;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.common.entity.TyUser;
import org.springframework.stereotype.Service;

@Service
public class TyUserServiceImpl extends ServiceImpl<UserInfoMapper, TyUser> implements TyUserService {


}
