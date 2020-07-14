package com.example.yywauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/5 11:11
 * @description
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/hi")
    public String hi(String name){
        return Optional.ofNullable(name).orElse("This is ApiController");
    }
}
