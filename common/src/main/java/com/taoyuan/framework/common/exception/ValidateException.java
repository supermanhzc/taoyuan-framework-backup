package com.taoyuan.framework.common.exception;

public class ValidateException extends TyException {
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
