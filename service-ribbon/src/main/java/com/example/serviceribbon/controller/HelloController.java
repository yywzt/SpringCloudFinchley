package com.example.serviceribbon.controller;

import com.example.serviceribbon.ResponseData;
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
    public ResponseData hi(@RequestParam(value = "name",required = false,defaultValue = "hahaha") String name){
        return helloService.hiSdervice(name);
    }

    @RequestMapping(value = "/hi2")
    public ResponseData hi2(@RequestParam(value = "name",required = false,defaultValue = "hahaha2") String name){
        return new ResponseData(0,name);
    }
}
