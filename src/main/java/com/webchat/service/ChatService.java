package com.webchat.service;

import com.webchat.netty.ChatMsg;

public interface ChatService {



    /**
     * 保存聊天消息到数据库
     * @param chatMsg
     * @return
     */
    public String saveMsg(ChatMsg chatMsg);

}
