package com.webchat.enums;

public enum OperatorFriendRequestTypeEnum {
    IGNORE(0, "忽略"),
    PASS(1, "通过");

    public final Integer type;
    public final String msg;


    OperatorFriendRequestTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }



    public static String getMsgByType(Integer type) {
        for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
            if (type == operType.type) {
                return operType.msg;
            }

        }
        return null;
    }


}
