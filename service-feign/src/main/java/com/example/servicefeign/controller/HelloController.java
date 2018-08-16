package com.example.servicefeign.controller;

import com.example.servicefeign.interFace.ServiceHiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/16 9:53
 * @describe
 */
@RestController
public class HelloController {

    @Autowired
    ServiceHiClient serviceHiClient;

    @GetMapping(value = "/hello")
    public String sayHi(@RequestParam(value = "name",required = false,defaultValue = "hehehe") String name) {
        return serviceHiClient.sayHiFromClientOne( name );
    }

}
