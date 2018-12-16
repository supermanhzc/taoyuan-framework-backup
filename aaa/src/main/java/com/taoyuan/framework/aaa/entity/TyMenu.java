package com.taoyuan.framework.aaa.entity;

import com.taoyuan.framework.common.entity.TyPermission;
import lombok.Data;

import java.util.List;

@Data
public class TyMenu {

    private String path;
    private String name;
    private String component;
    private String icon;
    private List<TyMenu> children;

    public TyMenu(TyPermission permission){
        if(null != permission){
            this.path = permission.getUrl();
            this.name = permission.getName();
            this.component = permission.getView();
            this.icon = permission.getUrl();
        }
    }
}
