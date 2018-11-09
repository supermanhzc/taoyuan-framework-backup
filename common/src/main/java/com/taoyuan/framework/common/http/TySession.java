package com.taoyuan.framework.common.http;

import com.taoyuan.framework.common.entity.TyUserRolePermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class TySession {
    public static TyUserRolePermission getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            return (TyUserRolePermission) subject.getSession().getAttribute(subject.getSession().getId().toString());
        }
        return null;
    }
}
