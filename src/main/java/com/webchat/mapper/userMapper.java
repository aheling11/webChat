package com.webchat.mapper;

import com.webchat.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {

    @Insert("insert into user (id, username, password, nickname, face_image, face_image_big, qrcode, cid) values (#{id}, #{username}, #{password}, #{nickname}, #{face_image}, #{face_image_big}, #{qrcode}, #{cid})")
    void insert(User user);

    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("select * from user where username = #{username} and password = #{password}")
    User findByUsernameAndPwd(@Param("username") String ussername, @Param("password") String password);

}
