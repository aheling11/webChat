package com.webchat.service.impl;

import com.webchat.enums.SearchFriendsStatusEnum;
import com.webchat.mapper.MyfriendsMapper;
import com.webchat.mapper.userMapper;
import com.webchat.pojo.User;
import com.webchat.service.UserService;
import idworker.Sid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userMapper userMapper;

    @Autowired
    private MyfriendsMapper myfriendsMapper;

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

}
