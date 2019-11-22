package com.webchat.mapper;

import com.webchat.pojo.FriendsRequest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FriendsRequestMapper {

    @Insert("insert into friends_request (id, send_user_id, accept_user_id, request_time) values (#{id}, #{send_user_id}, #{accept_user_id}, #{request_time})")
    void insert(FriendsRequest friendsRequest);

    @Select("select * from friends_request where send_user_id = #{send_user_id} and accept_user_id = #{accept_user_id}")
    FriendsRequest selectBySendIdAndAcceptId(@Param("send_user_id") String send_user_id,@Param("accept_user_id") String accept_user_id);

    @Delete("delete from friends_request where send_user_id = #{send_user_id} and accept_user_id = #{accept_user_id}")
    void deleteBySendUesrIdAndAcceptUserId(@Param("send_user_id") String send_user_id,@Param("accept_user_id") String accept_user_id);
}
