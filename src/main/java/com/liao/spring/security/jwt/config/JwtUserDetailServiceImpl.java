package com.liao.spring.security.jwt.config;

import com.liao.spring.security.jwt.mapper.MenuMapper;
import com.liao.spring.security.jwt.mapper.RoleMapper;
import com.liao.spring.security.jwt.mapper.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liao
 * @since 2020/5/28 14:33
 */
@Component
public class JwtUserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        // 加载基础权限信息
        JwtUserDetail userDetails = userMapper.getUserDetailsByUsername(username);
        //  判断是否加载到用户
        if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
            throw new UsernameNotFoundException(String.format("用户%s不存在", username));
        }

        // 加载角色信息
        List<String> roles = roleMapper.getRoleCodesByUsername(username);
        List<String> authorities = menuMapper.getMenuUrlsByRoleCodes(roles);
        roles = roles.stream().map(role -> "ROLE_" + role).collect(Collectors.toList());
        authorities.addAll(roles);
        // 设置用户权限
        userDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", authorities)));
        // 通过用户角色列表加载资源权限列表
        return userDetails;
    }
}
