package com.taoyuan.framework.common.exception;

import lombok.Data;

@Data
public class TyException extends RuntimeException {

    private static final long serialVersionUID = 1107245411496060098L;

    private Integer code;

    private String msg;

    private Object[] params;

    public TyException(Integer code,String msg) {
        this(code,msg,null);
    }

    public TyException(Integer code,String msg, Object param) {
        this(code,msg, new Object[] { param });
    }

    public TyException(Integer code,String msg, Object[] params) {
        this(code,msg, params, null);
    }

    public TyException(Integer code, String msg, Object[] params, Throwable cause) {
        super(msg,cause);
        this.code = code;
        this.params = params;
    }
}
