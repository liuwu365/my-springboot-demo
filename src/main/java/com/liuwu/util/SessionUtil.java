package com.liuwu.util;

import com.liuwu.entity.permission.BackUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.Objects;

/**
 * @author Created by 王亚平 on 2017/6/26.
 */
public class SessionUtil {

    public static BackUser getCurrentUser() {
        BackUser backUser = (BackUser) getSession().getAttribute(Constant.BACK_USER_INFO);
        return CheckUtil.isEmpty(backUser) ? new BackUser() : backUser;
    }

    public static Long getUserId() {
        return getCurrentUser().getId();
    }


    public static String getSessionId() {
        return SecurityUtils.getSubject().getSession().getId().toString();
    }

    public static void updateBackUser(BackUser backUser) {
        if (Objects.isNull(backUser)) {
            return;
        }
        getSession().setAttribute(Constant.BACK_USER_INFO, backUser);
    }


    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

}
