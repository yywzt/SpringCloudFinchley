package com.example.serviceribbon.controller;

import com.example.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/15 15:51
 * @describe
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam(value = "name",required = false,defaultValue = "hahaha") String name){
        return helloService.hiSdervice(name);
    }
}
