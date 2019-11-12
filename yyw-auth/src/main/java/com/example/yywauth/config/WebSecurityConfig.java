package com.example.yywauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author yanzt
 * @date 2018/7/5 17:06
 * @description 配置SpringSecurity
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userServices;
    /**放行这几种请求*/
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS|POST|PUT)$");
    /**对于未放开的put post请求需要传入_csrf.token*/
    /*private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 解决访问localhost:8080/druid 登陆失败，返回403问题
         * 原因是CSRF导致
         * 解决方法 ：
         *  1、关闭CSRF，不过这种办法不友好， http.csrf().disable();
         *  2、对druid开放
         * */
        http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
            @Override
            public boolean matches(HttpServletRequest request) {

                if(allowedMethods.matcher(request.getMethod()).matches()){
                    return false;
                }

                String servletPath = request.getServletPath();
                if(servletPath.contains("/druid")){
                    return false;
                }
                return true;
            }
        });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServices);
    }

    /**
     * 自定义的passwordEncoder，使用md5加密
     * */
//    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                //DigestUtils.md5DigestAsHex md5加密工具
                return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
            }
        };
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
