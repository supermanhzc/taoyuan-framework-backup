package com.taoyuan.framework.aaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuan.framework.aaa.config.TyRealm;
import com.taoyuan.framework.aaa.dao.UserInfoMapper;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.constant.UserConsts;
import com.taoyuan.framework.common.entity.TyUser;
import com.taoyuan.framework.common.entity.TyUserRolePermission;
import com.taoyuan.framework.common.exception.TyExceptionUtil;
import com.taoyuan.framework.common.exception.ValidateException;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySession;
import com.taoyuan.framework.common.http.TySuccessResponse;
import com.taoyuan.framework.common.util.TyDateUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;

@Service
public class TyUserServiceImpl extends ServiceImpl<UserInfoMapper, TyUser> implements TyUserService {


    @Autowired
    private HashedCredentialsMatcher hashedCredentialsMatcher;

    @Override
    public boolean register(TyUser userInfo) {
        Date currentDate = new Date();
        String newPassword = this.getEncodePassword(userInfo.getUserName(), userInfo.getPassword(), TyDateUtils.convertDateToString(currentDate));

        userInfo.setPassword(newPassword);
        userInfo.setCreateTime(currentDate);
        userInfo.setStatus(UserConsts.INIT.ordinal());
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if (currentUser == null) {
            userInfo.setCreateUser(0l);
        } else {
            userInfo.setCreateUser(currentUser.getUserId());
        }
        return this.saveOrUpdate(userInfo);
    }

    private String getEncodePassword(String username, String password, String salt){
        TyRealm realm = new TyRealm();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                password, //密码
                ByteSource.Util.bytes(salt),//salt=username+salt
                realm.getName()  //realm name
        );

        return new SimpleHash(hashedCredentialsMatcher.getHashAlgorithmName(),
                authenticationInfo.getCredentials(),
                ByteSource.Util.bytes(authenticationInfo.getCredentialsSalt()),
                hashedCredentialsMatcher.getHashIterations()).toHex();
    }

    @Override
    public boolean modify(TyUser userInfo) {
        if(null == userInfo.getId()){
            throw new ValidateException("user id can't be null.");
        }
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if (null != currentUser) {
            userInfo.setUpdateUser(currentUser.getUserId());
        }
        if(null != userInfo.getPassword()){
            TyUser user = this.getUserById(userInfo.getId());
            String password = this.getEncodePassword(user.getUserName(), userInfo.getPassword(), TyDateUtils.convertDateToString(user.getCreateTime()));
            userInfo.setPassword(password);
        }
        return this.updateById(userInfo);
    }

    @Override
    public boolean delete(Long id) {
        TyUser user = this.getById(id);
        if(null != user){
            user.setStatus(UserConsts.DELETED.ordinal());
            return this.modify(user);
        }
        return false;
    }

    @Override
    public TyUser getUserByName(String name) {
        QueryWrapper<TyUser> wrapper = new QueryWrapper<TyUser>();
        wrapper.lambda().eq(TyUser::getName, name);
        return getOne(wrapper);
    }

    @Override
    public TyUser getUserById(Long id) {
        QueryWrapper<TyUser> wrapper = new QueryWrapper<TyUser>();
        wrapper.lambda().eq(TyUser::getId, id);
        return getOne(wrapper);
    }
}
