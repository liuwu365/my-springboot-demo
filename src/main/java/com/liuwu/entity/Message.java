package com.liuwu.entity;

import com.google.gson.annotations.SerializedName;
import com.liuwu.enums.MessageType;

import java.io.Serializable;

/**
 * Created by liuyuanzhou on 5/9/16.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 3913478739968089133L;

    @SerializedName("MsgId")
    private long msgId;

    @SerializedName("From")
    private long from;

    @SerializedName("FromName")
    private String fromName;

    @SerializedName("To")
    private long to;

    @SerializedName("Text")
    private String text;

    @SerializedName("MsgType")
    private int msgType = 0X01;

    @SerializedName("CreateTime")
    private long ts;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }


    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public boolean isGroupMsg() {
        return (msgType & MessageType.GROUP_MSG.getCode()) == MessageType.GROUP_MSG.getCode();
    }


}
