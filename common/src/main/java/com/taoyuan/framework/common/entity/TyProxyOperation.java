package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
@TableName(value = "admin_proxyoperation")
public class TyProxyOperation {
    private Long id;

    private Long proxyId;

    private String proxyName;

    //账户变动
    private BigDecimal moneyChanged;

    //余额
    private BigDecimal account;

    //类型：1代充，2充值，3提现，4登录，5回收
    private int type;

    //描述
    private String description;

    private Date time;
}
