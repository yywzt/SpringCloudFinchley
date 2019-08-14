package com.example.configclient.controller;

import com.example.configclient.config.Student;
import com.example.configclient.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/17 14:38
 * @describe
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * Could not resolve placeholder 'zuul.routes.api-a.path' in value "${zuul.routes.api-a.path}"异常
     *  原因：
     *      config-client的spring.application.name必须和自己的配置文件名相匹配。
     *      例如配置文件为：testConfig-test.properties
     *      这个时候对应的config-cleint的spring.application.name=testConfig,spring.cloud.config.profile=dev
     * */
    @Value("${test.username}")
    String username;
    @Value("${test.password}")
    String password;
    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/hi")
    public String hi(){
        return new StringBuffer(username).append(",").append(password).append(",").append(foo).toString();
    }

    @RequestMapping("/redis/get/{key}")
    public String get(@PathVariable String key){
        return redisUtil.get(key);
    }

    @RequestMapping("/redis/set")
    public void get(@RequestParam String key, @RequestParam String value){
        redisUtil.set(key, value);
    }

    @Autowired
    Student student;

    @RequestMapping("/getStudent")
    public Student getStudent(){
        return student;
    }
}
