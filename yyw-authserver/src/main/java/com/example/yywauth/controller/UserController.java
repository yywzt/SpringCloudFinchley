package com.example.yywauth.controller;

import com.example.yywauth.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/12 14:53
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userservice;

    @RequestMapping("/encode")
    public String encode(String name){
        return userservice.encode(name);
    }
}
