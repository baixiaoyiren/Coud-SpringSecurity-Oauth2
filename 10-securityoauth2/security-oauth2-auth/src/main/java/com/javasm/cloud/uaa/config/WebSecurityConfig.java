package com.javasm.cloud.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasm.cloud.common.utils.AuthenticationRedisUtils;
import com.javasm.cloud.uaa.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * SpringSecurity配置
 * Created by macro on 2020/6/19.
 */
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig{

    @Resource
    AuthenticationRedisUtils redisUtils;


    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(){
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                List<String> ignoreUrls = redisUtils.getIgnoreUrls();
                ignoreUrls.add("/auth/oauth/check_token/*");
                String[] array = ignoreUrls.toArray(new String[0]);
                //for (String s : array) {
                //    System.out.println(s);
                //}
                http.formLogin();
                http
                        .csrf().disable()
                        //.formLogin()
                        //.loginPage("/uaa/login")
                        //.loginProcessingUrl("www.baidu.com")
                        //.and()
                        .logout()
                        .logoutUrl("**/logout").permitAll()
                        .and()
                        .authorizeRequests()
                        .antMatchers(array).permitAll()
                        .and()
                        .authorizeRequests()
                        .anyRequest()
                        .authenticated();
                http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
                http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

            }
        };
    }




    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                WebUtils.renderString(response,"未认证");
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                HashMap<String, String> map = new HashMap<>(2);
                map.put("uri", request.getRequestURI());
                map.put("msg", "你是不是没登录？");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ObjectMapper objectMapper = new ObjectMapper();
                String resBody = objectMapper.writeValueAsString(map);
                PrintWriter printWriter = response.getWriter();
                printWriter.print(resBody);
                printWriter.flush();
                printWriter.close();
            }
        };
    }


    /**
     * oauth2密码模式需要的 认证管理器
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(WebSecurityConfigurerAdapter webSecurityConfigurerAdapter) throws Exception {
        return webSecurityConfigurerAdapter.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
