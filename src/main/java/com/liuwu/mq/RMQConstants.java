package com.liuwu.mq;

/**
 * Created by liuyuanzhou on 5/10/16.
 */
public class RMQConstants {
    private static final String QUEUE_PREFIX = "yt_";

    private static final String USER_QUEUE_PREFIX = "u.";

    public static String getEventCenterKey(String queueName) {
        return QUEUE_PREFIX + queueName;
    }

    public static String getUserRouteKey(long uid) {
        return USER_QUEUE_PREFIX + uid;
    }
}
