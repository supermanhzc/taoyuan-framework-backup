package com.taoyuan.framework.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "ty_permission")
public class SysPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField(value = "parent_id")
    private Long parentId;
    @TableField(exist = false)
    private String parentIds;
    private String permission;
    @TableField(value = "resource_type")
    private String resourceType;
    private String url;
    private Boolean enabled;
}
