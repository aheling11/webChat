package com.webchat.mapper;


import com.webchat.pojo.MyFreinds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyfriendsMapper {

    @Select("select * from my_friends where my_user_id = #{myUserId} and my_friend_id = #{friendId}")
    MyFreinds findByMyIdAndFriendId(@Param("myUserId") String myUserId, @Param("friendId") String friendId);
}
