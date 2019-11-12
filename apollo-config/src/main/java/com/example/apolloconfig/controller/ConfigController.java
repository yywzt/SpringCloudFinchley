package com.example.apolloconfig.controller;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.example.apolloconfig.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ywyw2424@foxmail.com
 * @date 2019/9/17 22:51
 * @describe
 */
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("/config")
    public String method1(){
        return configService.printConfig();
    }

    @RequestMapping("/getConfigByKey")
    public Object getConfigByKey(@RequestParam(required = false,defaultValue = ConfigConsts.NAMESPACE_APPLICATION) String nameSpace, @RequestParam String key){
        return configService.getConfigByKey(nameSpace, key);
    }

}
