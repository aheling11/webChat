package com.webchat.controller;


import com.webchat.mapper.userMapper;
import com.webchat.model.User;
import com.webchat.utils.HeJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
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

    @GetMapping("/register")
    public HeJSONResult helloHeJson() {

        User user = new User();
        user.setUsername("hello");
        user.setPassword("322");
        user.setCid("");
        user.setFace_image("");
        user.setFace_image_big("");
        user.setNickname("");
        user.setQrcode("");
        userMapper.insert(user);
        return HeJSONResult.ok(user);
    }
}


