package com.liuwu.entity;

/**
 * Created by liuyuanzhou on 7/20/16.
 */
public enum ResultCode {
    OK(200, "成功"),
    FAIL(404, "失败"),
    ERROR(500, "服务器异常!"),
    BAD_REQUEST(400, "参数错误!"),
    TOKEN_IS_NULL(555, "token is null"),
    SERVER_INTERNAL_ERROR(20000, "服务器内部错误！"),
    NICKNAME_USE(20001, "该昵称已被占用"),
    REGISTER_FAIL(20002, "注册失败"),
    REGISTER_TYPE_ERROR(20003, "注册类型错误"),
    USERNAME_IS_NULL(20004, "用户名不能为空"),
    PASSWORD_IS_NULL(20005, "密码不能为空"),
    WX_LOGIN_USERINFO(20006, "获取微信用户信息失败"),
    THIRD_LOGIN_INSERT_USERINFO(20007, "导入用户信息失败"),
    WX_GET_ACCESS_TOKEN(20008, "获取accessToken失败"),
    THIRD_LOGIN_TYPE_ERROR(20009, "第三方登录类型错误"),
    QQ_LOGIN_USERINFO_ERROR(20010, "获取QQ用户信息失败"),
    SINA_LOGIN_USERINFO_ERROR(20011, "获取新浪微博用户信息失败"),
    APP_VERSION_ERROR(20012, "App版本不一致"),
    USER_NOT_EXIST(20013, "用户不存在"),
    PASSWORD_DIFFER(20014, "两次密码输入不一致"),
    RESET_PASSWORD_ERROR(20015, "重置密码错误"),
    INSERT_FAIL(20016, "保存失败"),
    LOGIN_OVERTIME(20017, "登录超时"),
    NOT_REPEAT_SUBMIT(20018, "添加月份已存在，不可重复提交！"),
    BANK_NUM_ALREADY_USED(20019, "银行卡号已被绑定!"),
    EMAIL_TYPE_ERROR(20020, "邮箱格式错误"),
    UPDATE_USER_INFO_ERROR(20021, "更新用户信息错误"),
    USER_ACCOUNT_NOT_REGISTER(20022, "您的账号未注册，请您先注册后再登录"),
    USER_ACCOUNT_LOCKED(20023, "账号已锁定"),
    USER_PASSWORD_ERROR(20024, "密码错误，还有几次输入机会"),
    USER_ACCOUNT_LOCKED_THIRTY(20025, "账号锁定30分钟"),
    OPERATE_ERROR(20026, "操作失败，稍后再试"),
    USER_NO_LOGIN(20027, "用户未登录"),
    USER_NOT_USE_COMPLAIN(20028, "账号未登陆，无法使用申诉系统"),
    WORK_ORDER_NOT_EXIST(20029, "工单号不存在!"),
    SET_TOKEN_FAIL(20030, "设置Token失败"),
    EMAIL_IS_EXIST(20031, "该邮箱已被占用"),
    SYSTEM_EXCEPTION(20032, "系统异常！"),
    GET_IDENTIFY_CODE_FAIL(20033, "获取验证码失败！"),
    NOT_BIND_EMAIL(20034, "用户未绑定邮箱，无法发送邮件"),
    MOBILE_NUM_EXIST(20035, "手机号码已注册"),
    WEB_SYS_ERROR(20036, "WEB系统异常"),
    MOBILENUM_IS_NULL(20037, "手机号码不能为空"),
    MOBILE_TYPE_ERROR(20038, "手机号码不合法"),
    SEND_MESSAGE_FREQUENTLY(20039, "消息发送太过频繁"),
    SEND_MESSAGE_IN_FIVE_MINUTE(20040, "5分钟内不能重发"),
    SEND_MESSAGE_TIME_MUCH_ERROR(20041, "30分钟不能超过5次"),
    SEND_MESSAGE_SYS_ERROR(20042, "发送验证码服务异常"),
    VERIFICATION_CODE_CONTINUE(20043, "请输入验证码并继续"),
    VERIFICATION_CODE_ERROR(20044, "验证码错误"),
    ORDER_OVER(20045, "订单已处理"),
    LOGUT_ERROR(20046, "退出登录失败"),
    ACCOUNT_IS_NULL(20047, "账号不能为空"),
    DATA_IS_NULL(20048, "查询失败，无数据记录"),
    REAL_NAME_IS_NULL(20049, "真实姓名不能为空"),
    WITHDRAW_PWD_IS_BULL(20050, "提现密码不能为空"),
    REAL_NAME_DIFF(20051, "已实名认证，请输入真实姓名"),
    CONFIRM_PASSWORD_IS_NULL(20052, "确认密码不能为空"),
    NEW_PASSWORD_IS_NULL(20053, "新密码不能为空"),
    OLD_PASSWORD_IS_NULL(20054, "旧密码不能为空"),
    OLD_NEW_PWD_IS_DIFF(20055, "新密码不可与原密码一致，请重新输入"),
    INPUT_PWD_IS_DIFF(20056, "您输入的密码不一致，请重新输入"),
    CHANGE_PASSWORD_FAIL(20057, "修改密码错误"),
    INPUT_OLD_PWD_ERROR(20058, "您输入的原密码有误，请检查后重新输入"),
    BIND_BANK_CARD_MUCH(20059, "你已超过绑定银行卡上限"),
    BANK_IS_NULL(20060, "开户银行不能为空"),
    BANK_CITY_IS_NULL(20061, "开户城市不能为空"),
    USER_BANK_REAL_NAME_NULL(20062, "开户人不能为空"),
    BANK_NUMBER_IS_NULL(20063, "银行卡号不能为空"),
    BANK_CONFIRM_NUMBER_IS_NULL(20064, "确认银行卡号不能为空"),
    INPUT_BANK_NUMBER_DIFF(20065, "两次输入银行卡号不一致"),
    DELETE_SECURITY_ERROR(20066, "删除密保问题失败"),
    VERIFICATION_CODE_IS_NULL(20066, "验证码不能为空"),
    UNBIND_YOURSELF_BANK_CARD(20067, "请解绑本人的银行卡"),
    UNBIND_BANK_CARD_FAIL(20068, "解绑银行卡失败"),
    BIND_TYPE_ERROR(20069, "绑定类型错误"),
    ACCOUNT_IN_USE(20070, "该账号已被绑定"),
    ADD_USER_EXT_INFO_FAIL(20071, "保存会员信息失败"),
    BANK_TYPE_ERROR(20072, "请输入正确的银行卡"),
    SEND_EMAIL_MUCH(20073, "邮件发送太频繁"),
    CHECK_SIGN_BAD(20074, "验签失败"),
    THIRD_PARTY_ERROR(20075, "第三方支付接口异常"),
    PAY_CHANNEL_ERROR(20076, "支付渠道错误"),
    NOT_BIND_BANK_CARD(20077, "未绑定银行卡"),
    FROZEN_MONEY_NOT_ZERO(20078, "您正在进行奖金任务，期间不能提款"),
    WITHDRAW_MONEY_TOO_MORE(20079, "您提款金额超过余额，请重新输入金额"),
    BEYOND_WITHDRAW_LIMIT(20080, "您已超过每日提款限额，请明日再提"),
    EMAIL_TOKEN_VERIFY_ERROR(20081, "邮箱验证失败"),
    EMAIL_IS_NULL(20082, "邮箱不能为空"),
    SECURITY_QUESTION_IDENTICAL(20083, "不能设置相同的密保问题"),
    CONFIRM_EMAIL_IS_NULL(20083, "确认邮箱不能为空"),
    EMAIL_IS_DIFF(20084, "两次邮箱输入不一致"),
    OLD_EMAIL_IS_NULL(20085, "原邮箱不能为空"),
    OLD_EMAIL_IS_ERROR(20086, "原邮箱输入有误")
    ;

    private int code;

    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getErrorDesc(int code) {
        for (ResultCode rc : ResultCode.values()) {
            if (rc.getCode() == code) {
                return rc.getDesc();
            }
        }
        return "";
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
