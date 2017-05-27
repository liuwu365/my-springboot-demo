package com.liuwu.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.gson.Gson;
import com.liuwu.biz.UserService;
import com.liuwu.entity.Page;
import com.liuwu.entity.User;
import com.liuwu.enums.UserStatus;
import com.xiaoleilu.hutool.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.maintain.Users;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:数据流(余额宝计息模拟)
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-26 下午 2:08
 */
public class MyDataFlowJob implements DataflowJob<User> {
    private static final Logger logger = LoggerFactory.getLogger(MyDataFlowJob.class);
    private static final Gson gson = new Gson();
    @Resource
    private UserService userService;

    @Override
    public List<User> fetchData(ShardingContext context) {
        //List<User> result = this.getData(context.getShardingParameter(), context.getShardingTotalCount());
        List<User> result = this.getData2(context.getShardingItem(), context.getShardingTotalCount());
        logger.info(String.format("Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s, Data: %s", Thread.currentThread().getId(), DateUtil.now(), context, "fetch data", result));
        return result;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
        //DB层方式1单个处理
        /*for (User user : data) {
            logger.info(String.format("用户 %s 开始计息", user.getId()));
            user.setStatus(UserStatus.DONE.getCode());
            this.updateData(user);
        }*/

        //DB层方式2批量处理
        List<User> userList = new ArrayList<>();
        for (int i = 0, j = data.size(); i < j; i++) {
            logger.info(String.format("用户 %s 开始计息", data.get(i).getId()));
            data.get(i).setStatus(UserStatus.DONE.getCode());
            userList.add(data.get(i));
            if (i > 100 && i % 100 == 0) {
                int res = userService.updateBatch(userList);
                if (res > 0) userList.clear();
            } else if (i == j - 1) {
                userService.updateBatch(userList);
            }
        }

    }


    /**
     * 一次查询了所有用户(用户量不是很大的情况)
     *
     * @param tailId
     * @param shardTotalCount
     * @return
     */
    public List<User> getData(String tailId, int shardTotalCount) {
        List<User> result = new ArrayList<>();
        int intId = Integer.parseInt(tailId);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            if (user.getId() % shardTotalCount == intId && user.getStatus() == UserStatus.TODO.getCode()) {
                result.add(user);
            }
        }
        logger.info("用户：{}", gson.toJson(result));
        return result;
    }

    /**
     * 分页查询用户
     *
     * @param shardingItem
     * @param shardTotalCount
     * @return
     */
    public List<User> getData2(int shardingItem, int shardTotalCount) {
        Map filter = new HashMap();
        filter.put("status", UserStatus.TODO.getCode());
        filter.put("shardingItem", shardingItem);
        filter.put("shardingTotalCount", shardTotalCount);
        Page<User> page = new Page<>();
        page.setOffset(0);
        page.setLimit(300);
        page.setFilter(filter);
        Page<User> userPage = userService.selectUsersToJob(page);
        logger.info("获取用户信息：{}", gson.toJson(userPage.getResult()));
        return userPage.getResult();
    }


    public void updateData(User user) {
        userService.updateUser(user);
    }

}
