package com.taoyuan.framework.common.entity;

import lombok.Data;

import java.util.Objects;

/**
 * 分页参数
 */
@Data
public class TyPageEntity {

    private Integer pegeIndex;

    private Integer pageSize;

}
