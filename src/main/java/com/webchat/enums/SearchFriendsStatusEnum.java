package com.webchat.enums;

public enum SearchFriendsStatusEnum {
    SUCCESS(0, "OK"),
    USER_NOT_EXIST(1, "没有此用户"),
    NOT_YOURSELF(2, "不能添加自己"),
    ALREADY_FRIEND(3,"该用户已经是你的好友");

    public Integer status;
    public String msg;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    SearchFriendsStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgByKey(Integer status) {
        for (SearchFriendsStatusEnum type : SearchFriendsStatusEnum.values()) {
            if (type.getStatus() == status) {
                return type.msg;
            }
        }
        return null;
    }


}
