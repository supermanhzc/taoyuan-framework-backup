package com.taoyuan.framework.common.exception;

public class ValidateException extends TyException {

    private static int ERROR_CODE = 100001;

    public ValidateException(String msg) {
        super(ERROR_CODE, msg);
    }

    public ValidateException(Integer code, String msg) {
        super(code, msg);
    }

    public ValidateException(Integer code, String msg, Object param) {
        super(code, msg, param);
    }

    public ValidateException(Integer code, String msg, Object[] param) {
        super(code, msg, param);
    }
}
