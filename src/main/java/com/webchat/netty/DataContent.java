package com.webchat.netty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DataContent {

    private static final long serialVersionUID = -5439668967366200256L;
    private Integer action;  //消息的类型
    private ChatMsg chatMsg; // 用户的聊天内容entity
    private String extand;  //扩展字段

    public Integer getAction() {
        return action;
    }

}
