package com.liuwu.controller;

import com.google.gson.Gson;
import com.liuwu.Helper.CommonHelper;
import com.liuwu.biz.UserService;
import com.liuwu.config.MyConfig;
import com.liuwu.entity.Page;
import com.liuwu.entity.Result;
import com.liuwu.entity.User;
import com.liuwu.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户控制类
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-03 下午 3:09
 */
@RestController
@RequestMapping("/admin")
public class UserController {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final MyConfig config = MyConfig.getInstance();
    private static CacheUtil cacheUtil = CacheUtil.getInstance();
    @Resource
    private UserService userService;

    @Value("#{otherConfig[otherName]}")
    private String userName;

    @RequestMapping("/index")
    private String index() {
        return "hello world!";
    }

    @RequestMapping("/login")
    private String login() {
        return String.format("hello %s", config.getName());
    }

    @RequestMapping("/login2")
    private String login2() {
        return String.format("hello %s", userName);
    }


    @RequestMapping("/getUserInfo/{userId}")
    private String getUserInfo(@PathVariable("userId") int userId, HttpServletRequest request) {
        //方法请求的频率检测
        Result tip = CommonHelper.checkHz(userId, request);
        if (tip.getCode() != 200) {
            return tip.getMsg();
        }
        User user = userService.getUserById(userId);
        cacheUtil.set("liuwu_user", gson.toJson(user), 24 * 60 * 60); //加入缓存
        return gson.toJson(user);
    }

    @RequestMapping("/getUserInfos/{offset}")
    private String getUserInfos(@PathVariable("offset") int offset) {
        Page<User> page = new Page<>();
        page.setLimit(2);
        page.setOffset(offset);
        Page<User> user = userService.getUserList(page);
        return gson.toJson(user);
    }

    @RequestMapping("/getUserInfos")
    private String getUserInfos() {
        Map filter = new HashMap();
        filter.put("status", 0);
        filter.put("id", 1);
        filter.put("shardingTotalCount", 2);
        filter.put("shardingItem", 1);
        Page<User> page = new Page<>();
        page.setLimit(30);
        page.setOffset(0);
        page.setFilter(filter);
        Page<User> userPage = userService.selectUsersToJob(page);
        logger.info("用户：{}", gson.toJson(userPage.getResult()));
        return gson.toJson(userPage);
    }


}
