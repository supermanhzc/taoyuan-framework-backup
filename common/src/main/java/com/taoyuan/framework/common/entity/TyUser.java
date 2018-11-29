package com.taoyuan.framework.common.entity;

import com.alibaba.druid.support.http.util.IPAddress;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "ty_user")
public class TyUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "username")
    private String userName;
    private String name;
    private String password;
    private String phone;
    private Integer status;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value="create_user")
    private Long createUser;
}
