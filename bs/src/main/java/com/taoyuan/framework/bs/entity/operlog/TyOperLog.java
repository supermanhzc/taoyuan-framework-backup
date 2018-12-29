package com.taoyuan.framework.bs.entity.operlog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.taoyuan.framework.common.entity.TyBaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "ty_oper_log")
public class TyOperLog extends TyBaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "start_time")
    private Date startTime;
    @TableField(value = "end_time")
    private Date endTime;
    private String ip;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "log_key")
    private String logKey;
    private String module;
    private String type;
    private String request;
    @TableField(value = "resp_data")
    private String respData;
    @TableField(value = "resp_code")
    private String respCode;
    @TableField(value = "resp_message")
    private String respMessage;
}
