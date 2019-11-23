package com.webchat.pojo;

import java.util.Date;

public class ChatMsg {
    private String id;
    private String send_user_id;
    private String receive_user_id;
    private String msg;
    private int sign_flag;
    private Date gmtCreate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(String send_user_id) {
        this.send_user_id = send_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSign_flag() {
        return sign_flag;
    }

    public void setSign_flag(int sign_flag) {
        this.sign_flag = sign_flag;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
