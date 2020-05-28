package com.liao.spring.security.jwt.mapper;

import com.liao.spring.security.jwt.domain.Role;

import java.util.List;

/**
  * @author liao
  * @since 2020/5/27 14:41
  */    
public interface RoleMapper {
    /**
     * 通过用户名加载用户的所有角色信息
     * @param username 用户名
     * @return list
     */
    List<Role> getRoleByUsername(String username);

    /**
     * 通过用户名加载用户的角色代码
     * @param username 用户名
     * @return list
     */
    List<String> getRoleCodesByUsername(String username);
}