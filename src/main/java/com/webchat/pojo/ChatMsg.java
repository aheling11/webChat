package com.webchat.model;

public class ChatMsg {
    private String id;
    private String send_uesr_id;
    private String receive_user_id;
    private String msg;
    private int sign_flag;
    private Long gmtCreate;
    private Long gmtModified;



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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSend_uesr_id() {
        return send_uesr_id;
    }

    public void setSend_uesr_id(String send_uesr_id) {
        this.send_uesr_id = send_uesr_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}
