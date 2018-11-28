package com.taoyuan.framework.common.http;

import com.taoyuan.framework.common.constant.ResultCode;

public class TySuccessResponse<T> extends TyResponse {
    public TySuccessResponse(T data) {
        setCode(ResultCode.SUCCESS.getCode().toString());
        setMsg(ResultCode.SUCCESS.getMsg());
        setData(data);
    }
}
