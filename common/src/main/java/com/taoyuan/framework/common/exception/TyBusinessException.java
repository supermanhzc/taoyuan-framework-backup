package com.taoyuan.framework.common.exception;

public class TyBusinessException extends TyException {

    private static int ERROR_CODE = 200001;

    public TyBusinessException(Integer code, String msg) {
        super(code, msg);
    }

    public TyBusinessException(String msg) {
        super(ERROR_CODE, msg);
    }
}
