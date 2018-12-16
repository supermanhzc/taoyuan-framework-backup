package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "ty_permission")
public class TyPermission implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(value = "parent_id")
    private Long parentId;
//    @TableField(value = "resource_type")
//    private String resourceType;
    private String url;
    private String view;
    private String icon;
    private String permission;
    private Integer order;
//    private Boolean enabled;
    private List<TyPermission> children;
}
