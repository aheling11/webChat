package com.webchat.mapper;


import com.webchat.pojo.vo.FriendRequestVO;
import com.webchat.pojo.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapperCustom {


    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

}
