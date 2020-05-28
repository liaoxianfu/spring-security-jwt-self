package com.liao.spring.security.jwt.mapper;

import com.liao.spring.security.jwt.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @author liao
  * @since 2020/5/27 14:54
  */    
public interface MenuMapper {
    List<String> getMenuByRoleId(int roleId);

    List<String> getMenuUrlsByRoleCodes(@Param("roles") List<String> roles);
}