package com.taoyuan.framework.aaa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.config.TyRealm;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.aaa.service.TyUserLoginService;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.constant.UserConsts;
import com.taoyuan.framework.common.entity.*;
import com.taoyuan.framework.common.exception.TyExceptionUtil;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySession;
import com.taoyuan.framework.common.http.TySuccessResponse;
import com.taoyuan.framework.common.util.TyDateUtils;
import com.taoyuan.framework.common.util.TyIpUtil;
import com.taoyuan.framework.oper.IProxyOperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.taoyuan.framework.common.constant.ErrorCode.USER_NOT_LOGGED_IN;

@Slf4j
@RestController
public class TyAuthController {

    @Autowired
    private TyUserService userService;

    @Autowired
    private TyRoleService roleService;

    @Autowired
    private TyPermissionService permissionService;

    @Autowired
    private HashedCredentialsMatcher hashedCredentialsMatcher;

    @Autowired
    private TyUserLoginService userLoginService;

    @Autowired
    private IProxyOperService proxyOperService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public TyResponse register(@RequestBody TyUser userInfo) {
        TyRealm realm = new TyRealm();

        Date currentDate = new Date();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(TyDateUtils.convertDateToString(currentDate)),//salt=username+salt
                realm.getName()  //realm name
        );

        String newPassword = new SimpleHash(hashedCredentialsMatcher.getHashAlgorithmName(),
                authenticationInfo.getCredentials(),
                ByteSource.Util.bytes(authenticationInfo.getCredentialsSalt()),
                hashedCredentialsMatcher.getHashIterations()).toHex();


        userInfo.setPassword(newPassword);
        userInfo.setCreateTime(currentDate);
        userInfo.setStatus(UserConsts.INIT.ordinal());

        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if (currentUser == null) {
            userInfo.setCreateUser(0l);
        } else {
            userInfo.setCreateUser(currentUser.getUserId());
        }
        boolean result = userService.saveOrUpdate(userInfo);
        return new TySuccessResponse(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TyResponse<TyUserRolePermission> login(@RequestBody TyUser userInfo) {
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());

        TyUserLoginEntity userLogin = new TyUserLoginEntity();
        try {
            subject.login(token);
            String sessionId = subject.getSession().getId().toString();
            TyUser user = userService.getOne(new QueryWrapper<TyUser>().eq("username", userInfo.getUsername()));

            List<TyRole> roles = roleService.selectRoleByUser(user);
            List<TyPermission> permissions = permissionService.selectPermByUser(user);

            TyUserRolePermission fullUser = new TyUserRolePermission(sessionId, user, roles, permissions);
            subject.getSession().setAttribute(sessionId, fullUser);
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            userLogin.setMemberId(user.getId());
            userLogin.setMemberNickName(userInfo.getUsername());
            String ip = TyIpUtil.getIpAddr(request);
            userLogin.setIp(ip);
            userLogin.setAddr(TyIpUtil.getAddressByIp(ip));
            userLogin.setType(getUseerType(fullUser.getRoles().get(0)));
            userLogin.setStatus(1);
            userLoginService.saveOrUpdate(userLogin);
            return new TySuccessResponse(fullUser);
        } catch (LockedAccountException e) {
            userLogin.setStatus(0);
            userLoginService.saveOrUpdate(userLogin);
            throw TyExceptionUtil.buildException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        } catch (ShiroException e) {
            userLogin.setStatus(0);
            userLoginService.saveOrUpdate(userLogin);
            throw TyExceptionUtil.buildException(ResultCode.USER_LOGIN_ERROR);
        } catch (Exception e) {
            userLogin.setStatus(0);
            userLoginService.saveOrUpdate(userLogin);
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
        throw TyExceptionUtil.buildException(ResultCode.USER_NOT_LOGGED_IN);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
    public TyResponse<String> heartbeat(){
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if (null != currentUser) {
            return new TySuccessResponse("heartbeat successfully.");
        }else{
            throw TyExceptionUtil.buildException(ResultCode.USER_NOT_LOGGED_IN);
        }


    }
    private int getUseerType(TyRole role) {
        String name = role.getName();
        if ("超级管理员".equals(name)) {
            return 1;
        } else if ("网站管理员".equals(name)) {
            return 2;
        } else if ("代理会员".equals(name)) {
            return 3;
        } else if ("钻石会员".equals(name)) {
            return 4;
        } else if ("普通会员".equals(name)) {
            return 5;
        }

        return 0;
    }

    //只记录代理的操作日志，其他不记录
    private void saveOperation(int type, String ip) {
        if (2 == type) {
            TyProxyOperation oper = new TyProxyOperation();
            //TODO真实数据，暂时写0
            oper.setAccount(BigDecimal.ZERO);
            oper.setDescription("登录");
            oper.setType(4);
            oper.setMoneyChanged(BigDecimal.ZERO);
            oper.setProxyId(TySession.getCurrentUser().getUserId());
            oper.setProxyName(TySession.getCurrentUser().getName());
            oper.setDescription(ip);
            proxyOperService.save(oper);
        }
    }
}
