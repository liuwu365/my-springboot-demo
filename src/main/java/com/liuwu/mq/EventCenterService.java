package com.liuwu.mq;

import com.yuantai.eventcenter.client.EventCenterFactory;

/**
 * Created by liuyuanzhou on 5/10/16.
 */
public interface EventCenterService {
    void publish(String key, String data, EventCenterFactory.EventPriority priority);

    void publish(String key, String data);
}
