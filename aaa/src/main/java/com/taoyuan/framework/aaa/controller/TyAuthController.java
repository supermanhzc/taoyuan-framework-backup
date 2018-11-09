package com.taoyuan.framework.aaa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.config.TyRealm;
import com.taoyuan.framework.aaa.entity.FullUserInfo;
import com.taoyuan.framework.aaa.entity.TyPermission;
import com.taoyuan.framework.aaa.entity.TyRole;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.aaa.service.UserInfoService;
import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.constant.UserConsts;
import com.taoyuan.framework.common.exception.TyExceptionUtil;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySuccessResponse;
import com.taoyuan.framework.common.util.TyDateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class TyAuthController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TyRoleService roleService;

    @Autowired
    private TyPermissionService permissionService;

    @Autowired
    private HashedCredentialsMatcher hashedCredentialsMatcher;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public TyResponse register(@RequestBody UserInfo userInfo){
        TyRealm realm = new TyRealm();

        Date currentDate = new Date();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(TyDateUtils.convertDateToString(currentDate)),//salt=username+salt
                realm.getName()  //realm name
        );

        String newPassword = new SimpleHash(hashedCredentialsMatcher.getHashAlgorithmName(), authenticationInfo.getCredentials(),
                ByteSource.Util.bytes(authenticationInfo.getCredentialsSalt()), hashedCredentialsMatcher.getHashIterations()).toHex();
        Subject subject = SecurityUtils.getSubject();

        userInfo.setPassword(newPassword);
        userInfo.setCreateTime(currentDate);
        userInfo.setStatus(UserConsts.INIT.ordinal());
        boolean result = userInfoService.saveOrUpdate(userInfo);
        return new TySuccessResponse(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TyResponse<UserInfo> login(@RequestBody UserInfo userInfo){
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());

        try {
            subject.login(token);
            String sessionId = subject.getSession().getId().toString();
            UserInfo user = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("username",userInfo.getUsername()));
            List<TyRole> roles = roleService.selectRoleByUser(user);
            List<TyPermission> permissions = permissionService.selectPermByUser(user);

            FullUserInfo fullUser = new FullUserInfo(sessionId, user, roles, permissions);
            subject.getSession().setAttribute(sessionId, fullUser);
            return new TySuccessResponse(fullUser);
        } catch (LockedAccountException e) {
            throw TyExceptionUtil.buildException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        } catch (ShiroException e) {
            throw TyExceptionUtil.buildException(ResultCode.USER_LOGIN_ERROR);
        } catch (Exception e) {
            throw TyExceptionUtil.buildException(ResultCode.FAIL.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @RequestMapping(value = "/unauth", method = RequestMethod.GET)
    public void unauth() {
        TyExceptionUtil.buildException(ResultCode.USER_NOT_LOGGED_IN);
    }
}
