package com.liao.spring.security.jwt.domain;

import java.util.Date;
import lombok.Data;

/**
  * @author liao
  * @since 2020/5/27 14:41
  */    
@Data
public class Role {
    private Integer id;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Integer sort;

    private Integer status;

    private Date createTime;
}