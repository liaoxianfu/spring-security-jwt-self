package com.liao.spring.security.jwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liao
 * @since 2020/5/28 15:40
 */

@Slf4j
@Component("rbacService")
public class JwtRbacService {
    @Resource
    private JwtService jwtService;
    public static AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 判断用户是否具有request的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        UserDetails details = principal instanceof UserDetails ? ((UserDetails) authentication.getPrincipal()) : null;
        if (details == null) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        String requestUri = request.getRequestURI();
        // 获取该用户能够有权访问的url
        List<String> urls = jwtService.getUrlsByUsername(username);
        return urls.stream().anyMatch(
                // 判断以*结尾的url能够访问任意的数据
                url -> {
                    log.info("获得的uri为{}", requestUri);
                    if (url.endsWith("/*")) {
                        String replace = url.replace("/*", "");
                        return requestUri.startsWith(replace);
                    }
                    return antPathMatcher.match(url, requestUri);
                }
        );
    }
}
