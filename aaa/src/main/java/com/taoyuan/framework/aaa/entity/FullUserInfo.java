package com.taoyuan.framework.aaa.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FullUserInfo implements Serializable{

    private String sessionId;
    private Long userId;
    private String userName;
    private String name;
    private String phone;
    private Integer status;
    private List<TyRole> roles;
    private List<TyPermission> permissions;

    public FullUserInfo(String sessionId, UserInfo user, List<TyRole> roles, List<TyPermission> permissions){
        this.setSessionId(sessionId);
        this.setUserId(user.getId());
        this.setUserName(user.getUsername());
        this.setName(user.getName());
        this.setPhone(user.getPhone());
        this.setRoles(roles);
        this.setPermissions(permissions);
        this.setStatus(user.getStatus());
    }
}
