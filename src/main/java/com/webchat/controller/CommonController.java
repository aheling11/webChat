package com.webchat.controller;

import com.webchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    UserService userService;


    @RequestMapping("/token")
    public String index() {
        return "Hello World";
    }

}