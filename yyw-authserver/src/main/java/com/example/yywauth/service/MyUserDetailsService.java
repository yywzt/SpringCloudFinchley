package com.example.yywauth.service;

import com.example.yywauth.mapper.UserMapper;
import com.example.yywauth.modal.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/10 11:35
 * @description
 */
@Slf4j
@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserInfByUname(username);
        if (user == null) {
            throw new UsernameNotFoundException("未查询到用户：" + username + "的信息！");
        }
        log.info("user：" + user);
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUname(), user.getPasswd(), authorities);
    }

}