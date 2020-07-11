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
    private JwtUserDetailServiceImpl userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取header
        String token = request.getHeader(jwtTokenUtil.getHeader());
        // 判断当请求头中发现了携带了jwt的请求头 就从jwt请求头中获取jwt
        // 从jwt中获取获取用户名 而且判断当前验证上下文中没有该数据 然后利用获取的用户名获取userDetails
        // 验证jwt与userDetails 判断jwt是否合法（是否过期 用户名是否正确） 验证成功后 进行授权
        // 最后 不管是否验证成功 都进行放行 进行后续的处理

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
