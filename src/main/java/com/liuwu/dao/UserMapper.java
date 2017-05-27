package com.liuwu.dao;

import com.liuwu.entity.Page;
import com.liuwu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @return
     */
    List<User> selectUserByPage(Page<User> page);

    int selectUserCountByPage(Map map);

    List<User> selectAllUsers();

    List<User> selectUsersToJob(Page<User> page);

    int selectUsersCountToJob(Map map);

    int updateBatch(List<User> userList);

}