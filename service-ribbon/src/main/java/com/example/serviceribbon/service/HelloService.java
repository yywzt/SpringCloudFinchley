package com.example.serviceribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yanzt
 * @date 2018/8/15 15:59
 * @describe
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    public String hiSdervice(String name){
        return restTemplate.getForObject("http://SERVER-TEST/hi?name="+name,String.class);
    }
}
