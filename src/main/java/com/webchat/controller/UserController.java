package com.webchat.controller;


import com.webchat.enums.OperatorFriendRequestTypeEnum;
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
     * 发送添加好友请求
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
            //1. 将该次请求记录进好友请求表
            userService.sendFriendRequest(myUserId, friendUsername);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return HeJSONResult.errorMsg(errorMsg);
        }

        return HeJSONResult.ok();
    }

    /**
     * 查询用户接收到的好友申请
     * @param userId ：你的用户ID
     * @return
     */
    @PostMapping("queryFriendRequest")
    public HeJSONResult queryFriendRequest(String userId) {
        if (StringUtils.isBlank(userId)) {
            return HeJSONResult.errorMsg("输入不能为空");
        }
        return HeJSONResult.ok(userService.queryFriendRequestList(userId));
    }


    /**
     * 用户处理收到的好友请求
     * @param sendUserId
     * @param acceptUserId
     * @param operType
     * @return
     */
    @PostMapping("/operFriendRequest")
    public HeJSONResult operFriendRequest(String sendUserId, String acceptUserId, Integer operType) {
        //0.判断输入不能为空
        if (StringUtils.isBlank(sendUserId) || StringUtils.isBlank(acceptUserId) || operType == null) {
            return HeJSONResult.errorMsg("输入不能为空");
        }
        //1. 如果传入的操作类型在enum中没有则抛出错误
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
            return HeJSONResult.errorMsg("无效的操作类型");
        }

        //2.如果忽略，则删除好友请求的数据库表记录
        if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
            userService.deleteFriendRequest(sendUserId, acceptUserId);
        } else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
            //3.先互相添加好友，再删除好友请求的数据库表记录
            userService.passFriendRequest(sendUserId, acceptUserId);
        }

        return HeJSONResult.ok();
    }


    /**
     * 返回用户的好友列表
     * @param myUserId
     * @return
     */
    @PostMapping("/queryFriends")
    public HeJSONResult queryFriendList(String myUserId) {
        //0.判断输入不能为空
        if (StringUtils.isBlank(myUserId)) {
            return HeJSONResult.errorMsg("输入不能为空");
        }

        return HeJSONResult.ok(userService.queryMyFriends(myUserId));
    }
}


