package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/17 14:38
 * @describe
 */
@RestController
public class ConfigClientController {

    @Value("${democonfigclient.message}")
    String username;

    @RequestMapping(value = "/hi")
    public String hi(){
        return username;
    }
}
