package com.liao.spring.security.jwt.domain;

import java.util.Date;
import lombok.Data;

/**
  * @author liao
  * @since 2020/5/27 13:54
  */    
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private Date createDatetime;

    private Integer orgId;

    private Byte enable;
}