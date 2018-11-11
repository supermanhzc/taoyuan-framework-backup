package com.taoyuan.framework.common.constant;

public enum StatusCode {
    /* 失败状态码 */
    UNKONW(-1, "未知"),

    /* 失败状态码 */
    FAIL(0, "失败"),

    /* 成功状态码 */
    SUCCESS(1, "成功");

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private StatusCode(Integer code) {
        this.code = code;
    }

    private StatusCode(String msg) {
        this.msg = msg;
    }
}
