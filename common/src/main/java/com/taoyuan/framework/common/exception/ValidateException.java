package com.taoyuan.framework.common.exception;

public class ValidateException extends TyException {
    public ValidateException(Integer code, String msg) {
        super(code, msg);
    }
}
