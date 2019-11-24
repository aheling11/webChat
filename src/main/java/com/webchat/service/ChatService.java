package com.webchat.service;

import com.webchat.netty.ChatMsg;

import java.util.List;

public interface ChatService {



    /**
     * 保存聊天消息到数据库
     * @param chatMsg
     * @return
     */
    public String saveMsg(ChatMsg chatMsg);


    /**
     * 批量签收消息
     * @param msgIdList
     */
    public void updateMsgSigned(List<String> msgIdList);

}
