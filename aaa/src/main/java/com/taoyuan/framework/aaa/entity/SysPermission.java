package com.taoyuan.framework.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Long parentId;
    private String parentIds;
    private String permission;
    private String resourceType;
    private String url;
    @TableField(value = "available")
    private Boolean isAvailable;
}
