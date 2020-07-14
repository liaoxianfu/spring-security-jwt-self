package com.liao.spring.security.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liao
 * @since 2020/5/26 10:39
 */

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtil {
    private String secret;
    private Long expiration;
    private String header;


    /**
     * 生成token
     */
    public String generateToken(UserDetails userDetails) {
        Claims claims = new DefaultClaims();
        claims.setSubject(userDetails.getUsername());
        return generateToken(claims);
    }


    /**
     * 生成jwt token
     * @author liao
     * @param claims {@link Claims}
     * @return jwt-token
     *
     */
    private String generateToken(Claims claims) {
        // 过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 通过token获取用户名
     */
    public String getUserNameFromToken(String token) {

        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            log.error("getUserNameFromToken error error info{}", e.getMessage());
            userName = null;
        }
        return userName;
    }

    /**
     * 通过token获取claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            // 解析获取claims
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("getClaimsFromToken error error info {}", e.getMessage());
        }
        return claims;
    }

    /**
     * 判断token是否过期
     */
    public boolean isTokenNotExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.error("获取过期时间出错,{}", e.getMessage());
            // 获取token出错之后默认设置为过期
            return false;
        }
    }


    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return generateToken(claims);
        } catch (Exception e) {
            log.error("refreshToken error err info{}", e.getMessage());
            return null;
        }
    }

    /**
     *  验证token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        // 判断token是否过期和用户名是否正确
        return isTokenNotExpired(token) && userName.equals(userDetails.getUsername());
    }

}
