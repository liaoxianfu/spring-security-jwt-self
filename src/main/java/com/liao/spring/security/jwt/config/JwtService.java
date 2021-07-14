package com.liao.spring.security.jwt.config;

import com.liao.spring.security.jwt.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liao
 * @since 2020/5/26 11:30
 */
@Service
@Slf4j
public class JwtService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    JwtUserDetailServiceImpl jwtUserDetailService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;
    @Resource
    private RedisTemplate<String, Boolean> jwtRedisTemplate;

    /**
     * 登录认证获取令牌
     */
    public String login(String username, String password) throws UsernameNotFoundException {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            log.error("构建Authentication失败 error info {}", e.getMessage());
            throw new UsernameNotFoundException("登录失败，未找到用户 ");
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        // 将token加入进redis中
        jwtRedisTemplate.opsForValue().set(token, true, jwtTokenUtil.getExpiration(), TimeUnit.MILLISECONDS);
        return token;

    }

    public String refreshToken(String oldToken) {
        if (jwtTokenUtil.isTokenNotExpired(oldToken)) {
            // 将原来的token删除
            jwtRedisTemplate.delete(oldToken);
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }

    public List<String> getUrlsByUsername(String username) {
        String key = this.getClass().getName() + "." + new Exception().getStackTrace()[0].getMethodName() + "." + username;
        List<String> urls = redisTemplate.opsForValue().get(key);
        if (urls != null && urls.size() > 0) {
            return urls;
        }
        urls = userMapper.getUrlsByUsername(username);
        redisTemplate.opsForValue().set(key, urls);
        return urls;
    }

    public Long getExpirationTime() {
        return jwtTokenUtil.getExpiration();
    }
}
