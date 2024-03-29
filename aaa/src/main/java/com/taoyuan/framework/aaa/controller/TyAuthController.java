package com.taoyuan.framework.aaa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.bs.aspect.OperControllerLog;
import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.constant.TyOperLogKeyConsts;
import com.taoyuan.framework.common.entity.TyPermission;
import com.taoyuan.framework.common.entity.TyRole;
import com.taoyuan.framework.common.entity.TyUser;
import com.taoyuan.framework.common.entity.TyUserRolePermission;
import com.taoyuan.framework.common.exception.TyExceptionUtil;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySession;
import com.taoyuan.framework.common.http.TySuccessResponse;
//import com.taoyuan.framework.oper.IProxyOperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TyAuthController {

    @Autowired
    private TyUserService userService;

    @Autowired
    private TyRoleService roleService;

    @Autowired
    private TyPermissionService permissionService;

//    @Autowired
//    private IProxyOperService proxyOperService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public TyResponse register(@RequestBody TyUser userInfo) {
        if(userService.register(userInfo)){
            return new TySuccessResponse("user register successfully.");
        }
        throw TyExceptionUtil.buildException(ResultCode.USER_REGISTRY_ERROR);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @OperControllerLog(key = TyOperLogKeyConsts.TY_LOGOUT_KEY, type = "用户退出登录", module = "AAA")
    public TyResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            return new TySuccessResponse("user logout successfully.");
        }catch (Exception e){
            throw TyExceptionUtil.buildException(ResultCode.USER_LOGOUT_ERROR);
        }
    }

    @RequestMapping(value = "/users/info", method = RequestMethod.GET)
    @OperControllerLog(type = "查询用户信息", module = "AAA")
    public TyResponse<TyUserRolePermission> getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        TyUserRolePermission currentUser = TySession.getCurrentUser();
        if(null != currentUser){
            List<TyRole> roles = null;
            try {
                TyUser tyUser = new TyUser();
                tyUser.setId(currentUser.getUserId());
                roles = roleService.selectRoleByUser(tyUser);
                List<TyPermission> permissions = permissionService.selectPermByUser(currentUser.getUserId());
                List<TyPermission> menus = permissionService.selectMenuByUser(currentUser.getUserId());
                currentUser.setRoles(roles);
                currentUser.setPermissions(permissions);
                currentUser.setMenus(menus);
                subject.getSession().setAttribute(subject.getSession().getId().toString(), currentUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TySuccessResponse<>(currentUser);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @OperControllerLog(key = TyOperLogKeyConsts.TY_LOGIN_KEY, type = "用户登录", module = "AAA")
    public TyResponse<TyUserRolePermission> login(@RequestBody TyUser userInfo) {
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword());

        try {
            subject.login(token);
            String sessionId = subject.getSession().getId().toString();
            TyUser user = userService.getOne(new QueryWrapper<TyUser>().eq("username", userInfo.getUserName()));

            List<TyRole> roles = roleService.selectRoleByUser(user);
            List<TyPermission> permissions = permissionService.selectPermByUser(user.getId());
            List<TyPermission> menus = permissionService.selectMenuByUser(user.getId());
            TyUserRolePermission fullUser = new TyUserRolePermission(sessionId, user, roles, permissions, menus);
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

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public TyResponse modify(@RequestBody TyUser userInfo){
        if(userService.modify(userInfo)){
            return new TySuccessResponse("user modify successfully.");
        }
        throw TyExceptionUtil.buildException(ResultCode.USER_UPDATE_ERROR);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public TyResponse delete(@PathVariable("id") Long id){
        if(userService.delete(id)){
            return new TySuccessResponse("user remove successfully.");
        }
        throw TyExceptionUtil.buildException(ResultCode.USER_REMOVE_ERROR);
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
}
