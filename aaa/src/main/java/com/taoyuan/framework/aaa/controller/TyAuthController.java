package com.taoyuan.framework.aaa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.bs.aspect.OperControllerLog;
import com.taoyuan.framework.common.constant.ResultCode;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @OperControllerLog(type = "用户登录", module = "AAA")
    public TyResponse<TyUserRolePermission> login(@RequestBody TyUser userInfo) {
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword());

        try {
            subject.login(token);
            String sessionId = subject.getSession().getId().toString();
            TyUser user = userService.getOne(new QueryWrapper<TyUser>().eq("username", userInfo.getUserName()));

            List<TyRole> roles = roleService.selectRoleByUser(user);
            List<TyPermission> permissions = permissionService.selectPermByUser(user);

            TyUserRolePermission fullUser = new TyUserRolePermission(sessionId, user, roles, permissions);
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
//    private int getUseerType(TyRole role) {
//        String name = role.getName();
//        if ("超级管理员".equals(name)) {
//            return 1;
//        } else if ("网站管理员".equals(name)) {
//            return 2;
//        } else if ("代理会员".equals(name)) {
//            return 3;
//        } else if ("钻石会员".equals(name)) {
//            return 4;
//        } else if ("普通会员".equals(name)) {
//            return 5;
//        }
//
//        return 0;
//    }
//
//    //只记录代理的操作日志，其他不记录
//    private void saveOperation(int type, String ip) {
//        if (2 == type) {
//            TyProxyOperation oper = new TyProxyOperation();
//            //TODO真实数据，暂时写0
//            oper.setAccount(BigDecimal.ZERO);
//            oper.setDescription("登录");
//            oper.setType(4);
//            oper.setMoneyChanged(BigDecimal.ZERO);
//            oper.setProxyId(TySession.getCurrentUser().getUserId());
//            oper.setProxyName(TySession.getCurrentUser().getName());
//            oper.setDescription(ip);
//            proxyOperService.save(oper);
//        }
//    }
}
