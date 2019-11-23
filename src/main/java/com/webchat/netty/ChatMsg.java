package com.webchat.netty;

import java.io.Serializable;

public class ChatMsg  {

    private static final long serialVersionUID = -4438700049255156017L;
    private String senderId;
    private String receiverId;
    private String msg;    //发送的消息
    private String msgId; //用于消息的签收

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
