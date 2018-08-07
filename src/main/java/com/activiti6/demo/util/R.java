package com.activiti6.demo.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        this.put((String)"code", ResponseEnum.SUCCESS.getCode());
        this.put((String)"msg", ResponseEnum.SUCCESS.getMsg());
    }

    public R(ResponseEnum response) {
        this.put((String)"code", response.getCode());
        this.put((String)"msg", response.getMsg());
    }

    public static R error() {
        return error(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMsg());
    }

    public static R error(String msg) {
        return error(ResponseEnum.FAIL.getCode(), msg);
    }

    public static R error(HttpStatus code, String msg) {
        R r = new R();
        r.put((String)"code", code);
        r.put((String)"msg", msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put((String)"code", code);
        r.put((String)"msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put((String)"msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}