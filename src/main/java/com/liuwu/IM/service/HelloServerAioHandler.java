package com.liuwu.IM.service;

import com.liuwu.IM.common.HelloAbsAioHandler;
import com.liuwu.IM.common.HelloPacket;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

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

            HelloPacket resppacket = new HelloPacket();
            resppacket.setBody(("收到了你的消息，你的消息是:" + str).getBytes(HelloPacket.CHARSET));
            Aio.send(channelContext, resppacket);
        }
        return null;
    }


}