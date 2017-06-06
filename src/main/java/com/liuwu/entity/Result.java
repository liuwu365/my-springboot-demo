package com.liuwu.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/25.
 */
public class Result<T> implements Serializable {


    private static final long serialVersionUID = -7284167079213189980L;
    private int code;
    private String msg;

    private T t;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Result() {
    }

    public Result(T t) {
        this.code = 200;
        this.t = t;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.t = t;
    }

}
