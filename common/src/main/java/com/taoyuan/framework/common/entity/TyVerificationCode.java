package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 验证码
 */
@Data
@ToString
@EqualsAndHashCode
@TableName(value = "admin_verificationcode")
public class TyVerificationCode implements Serializable {

    //ID
    private int id;

    //接口名称:短信,邮件等
    private String infName;

    //短信对应手机号码，邮件对应邮箱地址
    private String dest;

    //类型:注册,找回密码,兑奖
    private String type;

    //验证码
    private String vCode;

    //时间
    private Timestamp time;
}
