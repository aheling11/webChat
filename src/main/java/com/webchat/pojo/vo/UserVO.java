package com.webchat.pojo.vo;

import lombok.Data;

@Data
public class UserVO {

    private String id;

    private String username;

    private String nickname;

    private String face_image;

    private String face_image_big;

    private String qrcode;
    
    private String token;

}
