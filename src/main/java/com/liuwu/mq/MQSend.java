package com.liuwu.mq;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by liuyuanzhou on 5/10/16.
 */
@Component
public class MQSend {
    private static final Logger logger = LoggerFactory.getLogger(MQSend.class);

    private static final Gson gson = new Gson();

    private String queueName = "default_queue";

    @Resource
    private EventCenterService eventCenterService;

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     * @return the queueName
     */
    public String getQueueName() {
        return queueName;
    }

    public void sent(String message) throws IOException {
        sent(message, this.queueName);
    }

    public <T> void sendNotifyMessage(T message, long uid) throws IOException {
        eventCenterService.publish(RMQConstants.getUserRouteKey(uid), getSendToMqMsg(message));
        logger.info("给用户" + uid + " Sent '" + gson.toJson(message) + "'");
    }

    public void sent(String message, String queue) throws IOException {
        eventCenterService.publish(RMQConstants.getEventCenterKey(queue), message);
    }

    public void sendToExchange(String message, String exchange) throws IOException {
        eventCenterService.publish(exchange, message);
    }

    private <T> String getSendToMqMsg(T msg) {
        MqMessage mqMessage = new MqMessage(msg.getClass().getName(), gson.toJson(msg));
        return gson.toJson(mqMessage);
    }
}
