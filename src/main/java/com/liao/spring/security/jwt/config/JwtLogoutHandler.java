package com.liao.spring.security.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liao
 * @version 1.0.0
 * @Description
 * @since 1.0.0
 * @since 2020/7/11 19:22
 */
@Component
public class JwtLogoutHandler implements LogoutSuccessHandler {
    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Resource
    RedisTemplate<String, Boolean> jwtRedisTemplate;

    @Resource
    ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (token != null) {
            jwtRedisTemplate.delete(token);
        }
        response.getWriter().write(objectMapper.writeValueAsString(R.success().data("info", "退出成功")));
    }
}
