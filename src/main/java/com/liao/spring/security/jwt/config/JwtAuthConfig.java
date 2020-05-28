package com.liao.spring.security.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2020/5/28 14:40
 */
@Configuration
public class JwtAuthConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtUserDetailServiceImpl jwtUserDetailService;

    @Resource
    private PasswordEncoder passwordEncoder;



    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf()
                /*允许js操作cookie*/
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/login", "refresh").permitAll()
                .antMatchers("/*").hasAnyRole("admin")
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
