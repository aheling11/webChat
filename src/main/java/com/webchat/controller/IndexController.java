package com.webchat.controller;


import com.webchat.mapper.userMapper;
import com.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private userMapper userMapper;

    @GetMapping("/hello")
    public User helloChat() {
        User user = new User();
        user.setUsername("hello");
        user.setPassword("322");
//        userMapper.insert(user);
        return user;
    }
}
