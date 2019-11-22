package com.webchat.mapper.generator.model;

public class MyFriends {
    private String id;

    private String myUserId;

    private String myFriendId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId == null ? null : myUserId.trim();
    }

    public String getMyFriendId() {
        return myFriendId;
    }

    public void setMyFriendId(String myFriendId) {
        this.myFriendId = myFriendId == null ? null : myFriendId.trim();
    }
}