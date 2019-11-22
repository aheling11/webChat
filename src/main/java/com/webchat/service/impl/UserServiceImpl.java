package com.webchat.service.impl;

import com.webchat.enums.SearchFriendsStatusEnum;
import com.webchat.mapper.FriendsRequestMapper;
import com.webchat.mapper.MyfriendsMapper;
import com.webchat.mapper.UserMapperCustom;
import com.webchat.mapper.userMapper;
import com.webchat.pojo.FriendsRequest;
import com.webchat.pojo.MyFreinds;
import com.webchat.pojo.User;
import com.webchat.pojo.vo.FriendRequestVO;
import com.webchat.pojo.vo.MyFriendsVO;
import com.webchat.service.UserService;
import idworker.Sid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userMapper userMapper;

    @Autowired
    private UserMapperCustom userMapperCustom;

    @Autowired
    private MyfriendsMapper myfriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;



    @Autowired
    private Sid sid;

    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = userMapper.findByUsername(username);
        return user != null;
    }

    @Override
    public User saveUser(User user) {

        String userId = sid.nextShort();
        user.setQrcode("");
        user.setId(userId);
        user.setFace_image_big("");
        user.setFace_image("");
        userMapper.insert(user);
        return user;
    }

    @Override
    public User queryUserForLoin(String username, String pwd) {
        User UserResult = userMapper.findByUsernameAndPwd(username, pwd);
        System.out.println(UserResult);
        return UserResult;
    }

    @Override
    public Integer preConditionQuery(String myUserid, String friendUsername) {
        User user = userMapper.findByUsername(friendUsername);
        //1.该用户不存在
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //2.搜索的用户如果是自己，返回不能添加自己
        if (user.getId().equals(myUserid)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        //3.如果该用户已经是你的好友，则返回该用户已经是你的好友
        if (myfriendsMapper.findByMyIdAndFriendId(myUserid, user.getId()) != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIEND.status;
        }


        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Override
    public User getUesrInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {
        // 获取好友的用户信息
        User friend = userMapper.findByUsername(friendUsername);

        FriendsRequest fre = friendsRequestMapper.selectBySendIdAndAcceptId(myUserId, friend.getId());
        if (fre == null) {
            //如果好友请求表中没有记录，则新增好友请求记录
            String requestId = sid.nextShort();
            FriendsRequest request = new FriendsRequest();
            request.setId(requestId);
            request.setSend_user_id(myUserId);
            request.setAccept_user_id(friend.getId());
            request.setRequest_time(new Date());
            friendsRequestMapper.insert(request);
        }
    }

    @Override
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return userMapperCustom.queryFriendRequestList(acceptUserId);
    }

    @Override
    public void deleteFriendRequest(String sendUesrId, String acceptUserId) {
        friendsRequestMapper.deleteBySendUesrIdAndAcceptUserId(sendUesrId, acceptUserId);
    }


    @Override
    public void passFriendRequest(String sendUesrId, String acceptUserId) {
        saveFriends(sendUesrId, acceptUserId);
        saveFriends(acceptUserId, sendUesrId);
        deleteFriendRequest(sendUesrId, acceptUserId);
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        return userMapperCustom.queryMyFriends(userId);
    }

    private void saveFriends(String sendUserId, String acceptUserId) {
        MyFreinds myFreinds = new MyFreinds();
        myFreinds.setId(sid.nextShort());
        myFreinds.setMy_user_id(sendUserId);
        myFreinds.setMy_friend_id(acceptUserId);
        myfriendsMapper.insert(myFreinds);
    }

}
