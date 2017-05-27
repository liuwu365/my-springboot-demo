package com.liuwu.biz.Impl;

import com.liuwu.biz.UserService;
import com.liuwu.dao.UserMapper;
import com.liuwu.entity.Page;
import com.liuwu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-04 下午 4:27
 */
@Service
public class UserImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<User> getUserList(Page<User> page) {
        List<User> userList = userMapper.selectUserByPage(page);
        int total = userMapper.selectUserCountByPage(page.getFilter());
        page.setTotal(total);
        page.setResult(userList);
        return page;
    }

    @Override
    public Page<User> selectUsersToJob(Page<User> page) {
        List<User> userList = userMapper.selectUsersToJob(page);
        int total = userMapper.selectUsersCountToJob(page.getFilter());
        page.setTotal(total);
        page.setResult(userList);
        return page;
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


}
