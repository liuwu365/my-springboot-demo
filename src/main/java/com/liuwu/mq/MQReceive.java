package com.liuwu.mq;

import com.google.gson.Gson;
import com.rabbitmq.client.ShutdownSignalException;
import com.yuantai.eventcenter.client.Event;
import com.yuantai.eventcenter.client.EventCenterFactory;
import com.yuantai.eventcenter.client.PushEventReceiver;
import com.yuantai.eventcenter.client.PushProcessor;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by liuyuanzhou on 5/10/16.
 */
@Component
public class MQReceive {
    private static final Logger logger = LoggerFactory.getLogger(MQReceive.class);

    private static final Gson gson = new Gson();

    private String queueName = "queue_name";

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    private int concurrency = 20;

    private PushEventReceiver pushEventReceiver;

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(getConcurrency());

    public MQReceive() {
        //添加shutdown hook，当StatusThreadServer被kill时，会调用,打印信息使用System.out，logger可能已经被卸载
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Receive shutdown signal" +
                        ", active count:" + ((ThreadPoolExecutor) executorService).getActiveCount() +
                        ", task count:" + ((ThreadPoolExecutor) executorService).getTaskCount() +
                        ", completed task count:" + ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
                executorService.shutdown();
                try {
                    executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Shutdown over" +
                        ", active count:" + ((ThreadPoolExecutor) executorService).getActiveCount() +
                        ", task count:" + ((ThreadPoolExecutor) executorService).getTaskCount() +
                        ", completed task count:" + ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
            }
        });
    }

    public void listener(final String[] routekeys) throws IOException, ShutdownSignalException, ConfigurationException {
        listener(routekeys, null);
    }

    public void listener(final String[] routekeys, Function<Event, Boolean> func) throws IOException, ShutdownSignalException, ConfigurationException {
        Configuration configuration = new PropertiesConfiguration("mq.properties");
        String[] hosts = configuration.getString("hosts").split(";");
        queueName = configuration.getString("queue_name") + "_" + InetAddress.getLocalHost().getHostAddress().replace(".", "_");

        pushEventReceiver = EventCenterFactory.createPushReceiver(hosts, queueName, EventCenterFactory.HaLevel.NORMAL, true);
        pushEventReceiver.binding(routekeys, new PushProcessor() {
            public void process(Event e) {
                String message = (String) e.getPayload();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (func != null) {
                                func.apply(e);
                            }
                            logger.info(" 从队列" + queueName + " Received '" + message + "'");
                        } catch (Exception ex) {
                            logger.error("[MQDATA RECEIVE] " + message, ex);
                            logger.info("处理队列消息出错 message = " + message, ex);
                        }
                    }
                });
            }
        });
    }

    public void addRouteKey(String key) {
        pushEventReceiver.addRouteKey(key);
    }

    public void removeRouteKey(String key) {
        pushEventReceiver.removeRouteKey(key);
    }

}

