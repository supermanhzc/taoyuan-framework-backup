package com.taoyuan.framework.common.exception;

import com.taoyuan.framework.common.constant.ResultCode;

/**
 * 异常工具类
 */
public class TyExceptionUtil {

    public static TyException buildException(Integer code) {
        ResultCode[] rcs = ResultCode.values();
        String msg = null;
        for (ResultCode rc : rcs) {
            if (rc.getCode().equals(code)) {
                msg = rc.getMsg();
                break;
            }
        }
        return new TyException(code, msg);
    }

    public static TyException buildException(Integer code, String msg) {
        return new TyException(code, msg);
    }

    public static TyException buildException(ResultCode resultCode) {
        return new TyException(resultCode.getCode(), resultCode.getMsg(), null, null);
    }

}
