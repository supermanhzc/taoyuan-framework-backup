package com.taoyuan.framework.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 */
@Data
public class TyPageEntity implements Serializable {

    private Integer pageIndex;

    private Integer pageSize;

}
