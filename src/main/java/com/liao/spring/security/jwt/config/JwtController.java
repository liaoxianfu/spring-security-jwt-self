package com.liao.spring.security.jwt.config;


import com.liao.spring.security.jwt.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2020/5/26 11:26
 */
@RestController
@Slf4j
public class JwtController {

    @Resource
    JwtService jwtService;


    @PostMapping("/login")
    public R loginHandler(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.error().data("登录出错,用户名或者密码为空").code(401);
        }
        try {
            String token = jwtService.login(username, password);
            String timeOut = System.currentTimeMillis() + jwtService.getExpirationTime() + "";
            log.info("currentTime："+System.currentTimeMillis());
            log.info("timeOut:  "+timeOut);
            return R.success().data("token", token).data("timeOut", timeOut);
        } catch (UsernameNotFoundException e) {
            return R.error().data(e.getMessage());
        }
    }

    @RequestMapping("/refresh")
    public R refreshTokenHandler(@RequestHeader("${jwt.header}") String oldToken) {
        log.info("刷新令牌");
        String token = jwtService.refreshToken(oldToken);
        String timeOut = System.currentTimeMillis() + jwtService.getExpirationTime() + "";
        log.info("currentTime："+System.currentTimeMillis());
        log.info("timeOut:  "+timeOut);
        log.info("token   "+token);
        return R.success().data("token", token).data("timeOut",timeOut);
    }
}
