package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzt
 * @date 2018/8/17 14:38
 * @describe
 */
@RestController
@RefreshScope
public class ConfigClientController {

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
}
