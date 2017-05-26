package com.liuwu.mq;

/**
 * Created by liuyuanzhou on 5/12/16.
 */
public class MqMessage<T> {
    public MqMessage(String className, String msg) {
        this.className = className;
        this.msg = msg;
    }

    private String className;

    private String msg;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
