package com.liuwu.IM.client;

import com.liuwu.IM.common.Const;
import com.liuwu.IM.common.HelloPacket;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-09 上午 10:51
 */
public class HelloClientStarter {

    //服务器节点
    public static Node serverNode = new Node("127.0.0.1", Const.PORT);

    //handler, 包括编码、解码、消息处理
    public static ClientAioHandler<Object, HelloPacket, Object> aioClientHandler = new HelloClientAioHandler();

    //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
    public static ClientAioListener<Object, HelloPacket, Object> aioListener = null;

    //断链后自动连接的，不想自动连接请设为null
    private static ReconnConf<Object, HelloPacket, Object> reconnConf = new ReconnConf<Object, HelloPacket, Object>(5000L);

    //一组连接共用的上下文对象
    public static ClientGroupContext<Object, HelloPacket, Object> clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);

    public static AioClient<Object, HelloPacket, Object> aioClient = null;
    public static ClientChannelContext<Object, HelloPacket, Object> clientChannelContext = null;

    /**
     * 启动程序入口
     */
    public static void main(String[] args) throws Exception {
        aioClient = new AioClient<>(clientGroupContext);
        clientChannelContext = aioClient.connect(serverNode);

        //连上后，发条消息玩玩
        send("hello world!");
        send("测试下");
    }

    private static void send(String message) throws Exception {
        HelloPacket packet = new HelloPacket();
        packet.setBody(message.getBytes(HelloPacket.CHARSET));
        Aio.send(clientChannelContext, packet);
    }


}
