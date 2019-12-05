package com.example.yywauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/5 16:44
 * @description
 */
@RestController
@RequestMapping("/notoken")
public class NoAuthController {

    @RequestMapping("/hello")
    public String hello(String name){
        return Optional.ofNullable(name).orElse("This is NoAuthController");
    }
}
