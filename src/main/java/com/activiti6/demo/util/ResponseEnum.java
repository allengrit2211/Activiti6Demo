package com.activiti6.demo.util;

public enum ResponseEnum {
    SUCCESS(0, "success"),
    FAIL(1, "未知异常，请联系管理员"),
    NO_LOGIN(-1, "登录失效，请重新登录"),
    NO_PERMISSION(2, "没有权限禁止访问");

    private int code;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
