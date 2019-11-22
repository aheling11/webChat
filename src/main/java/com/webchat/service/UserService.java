package com.webchat.service;

import com.webchat.pojo.FriendsRequest;
import com.webchat.pojo.User;
import com.webchat.pojo.vo.FriendRequestVO;
import com.webchat.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 保存用户进入数据库
     * @param user
     * @return
     */
    public User saveUser(User user);

    /**
     * 登录请求
     * @param username
     * @param pwd
     * @return
     */
    public User queryUserForLoin(String username, String pwd);

    /**
     * 添加好友前进行前置条件判断
     * @param myUserid
     * @param friendUsername
     * @return
     */
    public Integer preConditionQuery(String myUserid, String friendUsername);

    /**
     * 根据username返回User
     * @param username
     * @return
     */
    public User getUesrInfoByUsername(String username);

    /**
     * 发送添加好友请求，保存记录进数据库
     * @param myUserId
     * @param friendUsername
     */
    public void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * 查询向你申请好友的用户列表
     * @param acceptUserId
     * @return
     */
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * 删除好友请求
     * @param sendUesrId
     * @param acceptUserId
     */
    public void deleteFriendRequest(String sendUesrId, String acceptUserId);


    /**
     * 1. 保存好友
     * 2. 逆向保存好友
     * 3. 删除好友的请求记录
     * @param sendUesrId
     * @param acceptUserId
     */
    public void passFriendRequest(String sendUesrId, String acceptUserId);

    /**
     * 查看用户的好友
     * @param userId
     * @return
     */
    public List<MyFriendsVO> queryMyFriends(String userId);


}
