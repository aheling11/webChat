package com.webchat.controller;


import com.webchat.enums.SearchFriendsStatusEnum;
import com.webchat.pojo.User;
import com.webchat.pojo.vo.UserVO;
import com.webchat.service.UserService;
import com.webchat.utils.HeJSONResult;
import com.webchat.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public HeJSONResult register(@RequestBody User user) throws Exception {

        if ( user.getUsername() == null || user.getUsername() == "" || user.getPassword() == null || user.getPassword() == "") {
            return HeJSONResult.errorMsg("用户名或密码不能为空");
        }
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        if (usernameIsExist) {
            return HeJSONResult.errorMsg("该用户名已存在");
        }
        //如果用户没设置nickname则默认为username
        if (user.getNickname() == "" || user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }

        user.setPassword(MD5Util.encrypt(user.getPassword()));
        User userResult = userService.saveUser(user);
        UserVO uservo = new UserVO();
        BeanUtils.copyProperties(userResult, uservo);
        return HeJSONResult.ok(uservo);
    }

    @PostMapping("login")
    public HeJSONResult login(@RequestBody User user) {

        User userresult = userService.queryUserForLoin(user.getUsername(), MD5Util.encrypt(user.getPassword()));
        if (userresult == null) {
            return HeJSONResult.errorMsg("账号或密码错误");
        }
        UserVO userVO = new UserVO();
        System.out.println(userresult.getNickname());
        BeanUtils.copyProperties(userresult, userVO);
        return HeJSONResult.ok(userVO);

    }

    /**
     * 搜索好友
     * @param myUserId
     * @param friendUsername
     * @return 返回搜索到的用户信息，或者返回错误信息
     */
    @PostMapping("search")
    public HeJSONResult searchFriend(String myUserId, String friendUsername) {

        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return HeJSONResult.errorMsg("输入不能为空");
        }
        //1. 首先判断好友是否存在
        //2. 是否已经是好友
        //3. 添加的好友不能是自己
        Integer status = userService.preConditionQuery(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            User user = userService.getUesrInfoByUsername(friendUsername);
            UserVO result = new UserVO();
            BeanUtils.copyProperties(user, result);
            return HeJSONResult.ok(result);


        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return HeJSONResult.errorMsg(errorMsg);
        }
    }

    /**
     * 添加好友请求 TO DO
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @PostMapping("addFriendRequest")
    public HeJSONResult addFriendRequest(String myUserId, String friendUsername) {

        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return HeJSONResult.errorMsg("输入不能为空");
        }
        //1. 首先判断好友是否存在
        //2. 是否已经是好友
        //3. 添加的好友不能是自己
        Integer status = userService.preConditionQuery(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            User user;

        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return HeJSONResult.errorMsg(errorMsg);
        }
    }
}


