package com.liuwu.mq;

import com.yuantai.eventcenter.client.EventCenterFactory;
import com.yuantai.eventcenter.client.EventSender;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by liuyuanzhou on 5/10/16.
 */
@Service
public class EventCenterServiceImpl implements EventCenterService {

    private static final Logger logger = LoggerFactory.getLogger(EventCenterServiceImpl.class);

    private EventSender eventSenderNone;

    private EventSender eventSenderNormal;

    private EventSender eventSenderHigh;

    @PostConstruct
    public void init() throws ConfigurationException {
        Configuration configuration = new PropertiesConfiguration("mq.properties");
        String[] addrs = configuration.getString("hosts").split(";");
        eventSenderNone = EventCenterFactory.createSender(addrs,
                EventCenterFactory.EventPriority.NONE);
        eventSenderNormal = EventCenterFactory.createSender(addrs,
                EventCenterFactory.EventPriority.NORMAL);
        eventSenderHigh = EventCenterFactory.createSender(addrs,
                EventCenterFactory.EventPriority.HIGH);
    }

    @Override
    public void publish(String key, String data) {
        publish(key, data, EventCenterFactory.EventPriority.NORMAL);
    }

    @Override
    public void publish(String key, String data, EventCenterFactory.EventPriority priority) {
        switch (priority) {
            case NONE:
                eventSenderNone.asyncPub(key, data);
                break;
            case NORMAL:
                eventSenderNormal.asyncPub(key, data);
                break;
            case HIGH:
                eventSenderHigh.asyncPub(key, data);
                break;
        }
    }
}

