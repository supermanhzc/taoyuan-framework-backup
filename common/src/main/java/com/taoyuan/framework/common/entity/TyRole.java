package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "ty_role")
public class TyRole implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String desc;
    private Boolean enabled;
}
