package com.example.consulserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/15 13:37
 * @describe
 */
@RestController
public class IndexController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp",required = false) String name) {
        String s = "hi " + name + " ,i am from port:" + port;
        System.out.println(s);
        return s;
    }

}
