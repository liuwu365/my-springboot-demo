package com.liuwu.enums;

/**
 * @ClassName: OperLog
 * @Package com.lottery.gamble.manage.core.enums
 * @Description: 操作日志类型
 * @author: liuwu1189@dingtalk.com
 * @date: 2017-07-04 17:09:53
 */
public enum OperLog {
    BACK_USER(1, "后台用户日志"),
    WORK_ORDER(2, "工单日志"),
    PAY(3, "充值支付日志"),
    RESOURCE(4, "资源日志"),
    NEWS(5, "资讯管理日志"),
    PROXY(6, "代理日志"),
    FINANCIAL(7, "财务管理日志"),
    OPERATION(8, "运营管理日志")

    ;

    private int code;
    private String desc;

    OperLog(int code, String desc) {
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
