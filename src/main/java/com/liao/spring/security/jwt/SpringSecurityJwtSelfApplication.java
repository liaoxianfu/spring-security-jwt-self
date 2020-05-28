package com.liao.spring.security.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liao
 */
@SpringBootApplication

@MapperScan("com.liao.spring.security.jwt.mapper")
public class SpringSecurityJwtSelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtSelfApplication.class, args);
    }

}
