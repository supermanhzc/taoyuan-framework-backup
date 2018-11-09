package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.dao.UserInfoMapper;
import com.taoyuan.framework.aaa.entity.FullUserInfo;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


}
