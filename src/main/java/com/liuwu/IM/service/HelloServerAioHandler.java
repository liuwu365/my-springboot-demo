package com.liuwu.IM.service;

import com.liuwu.IM.common.HelloAbsAioHandler;
import com.liuwu.IM.common.HelloPacket;
import com.liuwu.IM.common.SessionContext;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.ibatis.annotations.One;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.liuwu.IM.client.HelloClientStarter.clientChannelContext;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-09 上午 10:28
 */

public class HelloServerAioHandler extends HelloAbsAioHandler implements ServerAioHandler<Object, HelloPacket, Object> {

    /**
     * 处理消息
     */
    @Override
    public Object handler(HelloPacket packet, ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception {
        byte[] body = packet.getBody();
        if (body != null) {
            String str = new String(body, HelloPacket.CHARSET);
            System.out.println("收到消息：" + str);

            // 绑定长连接
            //Aio.bindUser(channelContext, "1234");

            HelloPacket resppacket = new HelloPacket();
            resppacket.setBody(("收到了你的消息，你的消息是:" + str).getBytes(HelloPacket.CHARSET));
            Aio.send(channelContext, resppacket);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.current(false));
    }


    public static void aio() {
        HelloPacket hello = new HelloPacket();
        byte arr[] = {104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100};
        hello.setBody(arr);
        Aio.sendToUser(HelloServerStarter.serverGroupContext, "1234", hello);

    }

}