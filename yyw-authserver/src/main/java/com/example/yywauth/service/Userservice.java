package com.example.yywauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/12 14:54
 * @description
 */
@Service
public class Userservice {

    @Autowired
    PasswordEncoder passwordEncoder;

    public String encode(String name) {
        return passwordEncoder.encode(name);
    }
}
