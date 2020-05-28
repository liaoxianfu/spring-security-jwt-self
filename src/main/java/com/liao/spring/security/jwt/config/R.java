package com.liao.spring.security.jwt.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * @author liao
 */
@Data
public class R {
    private R() {
    }

    private int code;
    private String message;
    private Object data;
    private Map<Object, Object> dataMap = new HashMap<>();

    public static R success() {
        R r = new R();
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public R data(Object data) {
        this.setData(data);
        return this;
    }

    public static R error() {
        R r = new R();
        r.setCode(500);
        r.setMessage("失败");
        return r;
    }

    public R code(int code){
        this.code = code;
        return this;
    }

    public R data(String key, String value){
        this.dataMap.put(key, value);
        return this;
    }


}
