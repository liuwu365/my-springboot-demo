package com.liuwu.enums;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: UserStatus
 * @Package com.liuwu.enums
 * @Description:
 * @author: liuwu1189@dingtalk.com
 * @date: 2017-05-27 11:28:57
 */
public enum UserStatus {
    TODO(0, "待处理"),
    DONE(1, "已完成");

    private int code;
    private String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
