package com.liuwu.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.gson.Gson;
import com.liuwu.biz.UserService;
import com.liuwu.entity.User;
import com.liuwu.enums.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<User> result = this.getData(context.getShardingParameter(), context.getShardingTotalCount());
        logger.info(String.format("Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s, Data: %s", Thread.currentThread().getId(), new Date(), context, "fetch data", result));
        return result;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<User> data) {
        for (User user : data) {
            logger.info(String.format("用户 %s 开始计息", user.getId()));
            user.setStatus(0);
            this.updateData(user);
        }
    }

    public List<User> getData(String tailId, int shardTotalCount) {
        List<User> result = new ArrayList<>();
        int intId = Integer.parseInt(tailId);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            if (user.getId() % shardTotalCount == intId && user.getStatus() == UserStatus.DONE.getCode()) {
                result.add(user);
            }
        }
        logger.info("用户：{}", gson.toJson(result));
        return result;
    }

    public void updateData(User user) {
        userService.updateUser(user);
    }

}
