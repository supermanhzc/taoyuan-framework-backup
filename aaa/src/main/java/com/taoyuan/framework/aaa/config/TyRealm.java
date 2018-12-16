package com.taoyuan.framework.aaa.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.service.TyPermissionService;
import com.taoyuan.framework.aaa.service.TyRoleService;
import com.taoyuan.framework.aaa.service.TyUserService;
import com.taoyuan.framework.common.constant.UserConsts;
import com.taoyuan.framework.common.entity.TyPermission;
import com.taoyuan.framework.common.entity.TyRole;
import com.taoyuan.framework.common.entity.TyUser;
import com.taoyuan.framework.common.util.TyDateUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

public class TyRealm extends AuthorizingRealm {

    @Resource
    private TyRoleService roleService;

    @Resource
    private TyPermissionService permissionService;

    @Resource
    private TyUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        Tytem.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        try {
            TyUser userInfo = userService.getOne(new QueryWrapper<TyUser>().eq("username",username));
            List<TyRole> roles = roleService.selectRoleByUser(userInfo);
            for (TyRole role : roles) {
                authorizationInfo.addRole(role.getName());
            }
            List<TyPermission> TyPermissions = permissionService.selectPermByUser(userInfo.getId());
            for (TyPermission perm : TyPermissions) {
                authorizationInfo.addStringPermission(perm.getPermission());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
//        Tytem.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        TyUser userInfo = userService.getOne(new QueryWrapper<TyUser>().eq("username",username).ne("status", UserConsts.DELETED.ordinal()));
//        Tytem.out.println("----->>userInfo="+userInfo);
        if (userInfo == null) {
            throw new AuthenticationException();
        }
        if (userInfo.getStatus() == UserConsts.LOCKED.ordinal()) { //账户冻结
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUserName(), //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(TyDateUtils.convertDateToString(userInfo.getCreateTime())),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
