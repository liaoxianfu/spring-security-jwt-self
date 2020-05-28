package com.liao.spring.security.jwt.mapper;

import com.liao.spring.security.jwt.config.JwtUserDetail;

import java.util.List;

/**
  * @author liao
  * @since 2020/5/27 13:54
  */    
public interface UserMapper {


    /**
     * 通过用户名加载用户姓名、用户密码、用户是否运行登录
     * @param username 用户名
     * @return JwtUserDetails
     */
    JwtUserDetail getUserDetailsByUsername(String username);

    List<String> getUrlsByUsername(String username);



}