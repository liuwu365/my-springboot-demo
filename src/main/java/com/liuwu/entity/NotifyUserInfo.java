package com.liuwu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-11 下午 4:03
 */
@Data
@Accessors(chain = true)
public class NotifyUserInfo implements Serializable {
    private String msg;
    private User user;

}
