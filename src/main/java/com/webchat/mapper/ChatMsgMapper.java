package com.webchat.mapper;


import com.webchat.pojo.ChatMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMsgMapper {

    @Insert("insert into chat_msg (id, send_user_id, receive_user_id, msg, sign_flag, gmt_create) values (#{id}, #{send_user_id}, #{receive_user_id}, #{msg}, #{sign_flag}, #{gmtCreate})")
    void insert(ChatMsg chatMsg);

    public void batchUpdateMsgSigned(List<String> msgIdsList);

}
