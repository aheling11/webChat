package com.webchat.service.impl;

import com.webchat.enums.MsgActionEnum;
import com.webchat.enums.MsgSignFlagEnum;
import com.webchat.mapper.ChatMsgMapper;
import com.webchat.netty.ChatMsg;
import com.webchat.pojo.vo.MyFriendsVO;
import com.webchat.service.ChatService;
import idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatMsgMapper chatMsgMapper;

    @Autowired
    Sid sid;

    @Override
    public String saveMsg(ChatMsg msg) {
        com.webchat.pojo.ChatMsg chatMsg = new com.webchat.pojo.ChatMsg();

        String msgId = sid.nextShort();
        chatMsg.setId(msgId);
        chatMsg.setMsg(msg.getMsg());
        chatMsg.setSend_user_id(msg.getSenderId());
        chatMsg.setReceive_user_id(msg.getReceiverId());
        chatMsg.setGmtCreate(new Date());
        chatMsg.setSign_flag(MsgSignFlagEnum.unsign.type);

        chatMsgMapper.insert(chatMsg);
        return msgId;
    }
}
