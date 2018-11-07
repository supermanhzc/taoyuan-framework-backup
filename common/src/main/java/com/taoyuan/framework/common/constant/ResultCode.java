package com.taoyuan.framework.common.constant;

public enum ResultCode {

    /* 失败状态码 */
    UNKONW(0, "未知"),

    /* 失败状态码 */
    FAIL(0, "失败"),

    /* 成功状态码 */
    SUCCESS(1, "成功"),

    /* 参数错误：100001-199999 */
    PARAM_IS_INVALID(100001, "参数无效"),
    PARAM_IS_BLANK(100002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(100003, "参数类型错误"),
    PARAM_NOT_COMPLETE(100004, "参数缺失"),

    /* 用户错误：200001-299999*/
    USER_NOT_LOGGED_IN(200001, "用户未登录"),
    USER_LOGIN_ERROR(200002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(200003, "账号已被禁用"),
    USER_NOT_EXIST(200004, "用户不存在"),
    USER_HAS_EXISTED(200005, "用户已存在"),

    /* 业务错误：300001-399999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(300001, "某业务出现问题"),

    /* 系统错误：400001-499999 */
    SYSTEM_INNER_ERROR(400001, "系统繁忙，请稍后重试"),

    /* 数据错误：500001-599999 */
    RESULE_DATA_NONE(500001, "数据未找到"),
    DATA_IS_WRONG(500002, "数据有误"),
    DATA_ALREADY_EXISTED(500003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(600001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(600002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(600003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(600004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(600005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(600006, "接口负载过高");

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

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultCode(Integer code) {
        this.code = code;
    }

    private ResultCode(String msg) {
        this.msg = msg;
    }
}
