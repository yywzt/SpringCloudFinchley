package com.example.serviceribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yanzt
 * @date 2018/8/15 15:59
 * @describe
 *  首先需要 @EnableHystrix注解 开启Hystrix
 *  服务调用service方法上添加 @HystrixCommand 注解
 *      该注解对该发方法创建了熔断器的功能，
 *      fallbackMethod：指定熔方法
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String hiSdervice(String name){
        return restTemplate.getForObject("http://SERVICE-TEST/hi?name="+name,String.class);
    }

    public String error(String name){
        return new StringBuffer("hi,").append(name).append(",sorry,error!").toString();
    }
}
