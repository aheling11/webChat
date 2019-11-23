package com.webchat.netty;

import java.io.Serializable;

public class DataContent {

    private static final long serialVersionUID = -5439668967366200256L;
    private Integer action;  //消息的类型
    private ChatMsg chatMsg; // 用户的聊天内容entity
    private String extand;  //扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
