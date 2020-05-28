package com.liao.spring.security.jwt.domain;

import lombok.Data;

/**
  * @author liao
  * @since 2020/5/27 14:54
  */    
@Data
public class Menu {
    private Integer id;

    private String menuName;

    private String url;

    private Integer menuPid;

    private String menuPids;

    private Byte isLeaf;

    private Integer status;
}