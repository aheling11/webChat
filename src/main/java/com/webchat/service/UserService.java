package com.webchat.service;

import com.webchat.pojo.User;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    public User saveUser(User user);

    public User queryUserForLoin(String username, String pwd);
}
