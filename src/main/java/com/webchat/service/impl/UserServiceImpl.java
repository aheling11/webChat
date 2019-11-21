package com.webchat.service.impl;

import com.webchat.mapper.userMapper;
import com.webchat.pojo.User;
import com.webchat.service.UserService;
import idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userMapper userMapper;

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

}
