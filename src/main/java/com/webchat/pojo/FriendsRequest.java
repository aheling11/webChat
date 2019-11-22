package com.webchat.pojo;

import java.util.Date;

public class FriendsRequest {
    private String id;
    private String send_user_id;
    private String accept_user_id;
    private Date request_time;


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

    public String getAccept_user_id() {
        return accept_user_id;
    }

    public void setAccept_user_id(String accept_user_id) {
        this.accept_user_id = accept_user_id;
    }

    public Date getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Date request_time) {
        this.request_time = request_time;
    }


}
