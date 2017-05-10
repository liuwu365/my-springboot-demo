package com.liuwu.biz;

import com.liuwu.entity.Page;
import com.liuwu.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public interface UserService {

    /**
     * 获取用户列表
     * @return
     */
    Page<User> getUserList(Page<User> page);

    /**
     * 根据用户Id获取用户信息
     * @param userId
     * @return
     */
    User getUserById(int userId);

    List<User> getAllUsers();


}
