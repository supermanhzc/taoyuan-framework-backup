package com.taoyuan.framework.common.http;

public class TySuccessResponse<T> extends TyResponse {
    public TySuccessResponse(T data) {
        setCode("200");
        setMsg("成功");
        setData(data);
    }
}
