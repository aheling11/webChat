package com.webchat.mapper;

import com.webchat.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {

    @Insert("insert into user (username, password, nickname, face_image, face_image_big, qrcode, cid) values (#{username}, #{password}, #{nickname}, #{face_image}, #{face_image_big}, #{qrcode}, #{cid})")
    void insert(User user);


}
