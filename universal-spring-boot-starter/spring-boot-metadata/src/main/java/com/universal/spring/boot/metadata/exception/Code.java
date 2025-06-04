package com.universal.spring.boot.metadata.exception;

public enum Code {

    SYS_UNKNOWN(-10000, "未知错误"),
    SYS_SERVICE_ERROR(-10001, "服务错误"),
    SYS_NOT_FOUND(-10002, "没有找到数据"),
    SYS_INVALID_SIGN(-10003, "无效的签名"),
    SYS_ILLEGAL_ARGUMENT(-10004, "无效的参数"),
    SYS_INVALID_TOKEN(-10005, "无效的TOKEN"),
    SYS_EXPIRED_TOKEN(-10006, "TOKEN已过期"),
    SYS_INCOMPATIBLE_VERSION(-10007, "版本不兼容"),
    SYS_NO_PERMISSION(-10008,"没有权限"),

    USR_HAS_DISABLED(-11001, "账号已停用"),
    USR_INVALID_USERNAME(-11002, "无效的用户名"),
    USR_INVALID_PASSWORD(-11002, "无效的密码"),
    USR_NOT_ENOUGH_BALANCE(-11003, "余额不足"),
    USR_LOGIN_REQUIRED(-11004, "要求先登录");

    private int code;
    private String text;

    private Code(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String message) {
        this.text = message;
    }

    @Override
    public String toString() {

        return String.format("%s, %s", this.code, this.text);
    }
}
