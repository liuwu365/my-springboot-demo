package com.liuwu.biz;

import com.liuwu.entity.Page;
import com.liuwu.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public interface UserService {

    /**
     * 分页获取用户列表
     *
     * @return
     */
    Page<User> getUserList(Page<User> page);

    /**
     * 分页获取用户到定时任务
     *
     * @param page
     * @return
     */
    Page<User> selectUsersToJob(Page<User> page);

    /**
     * 根据用户Id获取用户信息
     *
     * @param userId
     * @return
     */
    User getUserById(int userId);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getAllUsers();

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 批量更新用户
     * @param userList
     * @return
     */
    int updateBatch(List<User> userList);

}
