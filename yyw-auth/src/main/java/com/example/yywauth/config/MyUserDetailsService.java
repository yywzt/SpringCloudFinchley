package com.example.yywauth.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/11/12 18:39
 * @description
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInf user = new UserInf();
        user.setUname(username);
        user.setPasswd("123456");
        return user;
    }
}
