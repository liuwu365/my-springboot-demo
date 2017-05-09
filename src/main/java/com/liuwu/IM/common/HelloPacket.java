package com.liuwu.IM.common;

import org.tio.core.intf.Packet;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-05-09 上午 10:15
 */
public class HelloPacket extends Packet {
    public static final int HEADER_LENGHT = 4;//消息头的长度
    public static final String CHARSET = "utf-8";
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
