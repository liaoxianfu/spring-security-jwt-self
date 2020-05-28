package com.liao.spring.security.jwt.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liao
 * @since 2020/5/26 17:58
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    JwtUserDetailServiceImpl userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取header
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isEmpty(token)) {
            String userName = jwtTokenUtil.getUserNameFromToken(token);
            // 判断用户名是否为空
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                boolean validateToken = jwtTokenUtil.validateToken(token, userDetails);
                if (validateToken) {
                    // 给使用该jwt的用户进行授权
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
