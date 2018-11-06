package com.taoyuan.framework.common.http;

import lombok.Data;

@Data
public class TyResponse<T> {

    private String code;
    private String msg;
    private T data;
}
