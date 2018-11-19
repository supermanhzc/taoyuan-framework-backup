package com.taoyuan.framework.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * 会员登陆
 */
@Data
@ToString
@EqualsAndHashCode
@TableName(value = "admin_userlogin")
public class TyUserLoginEntity {
    private Long id;

    //类型 1会员，2代理，3管理员
    private int type;

    //ID
    private Long memberId;

    //昵称
    private String memberNickName;

    //状态 1:成功 2:失败
    private int status;

    //IP
    private String ip;

    //地址
    private String addr;

    //时间
    private Timestamp time;
}
