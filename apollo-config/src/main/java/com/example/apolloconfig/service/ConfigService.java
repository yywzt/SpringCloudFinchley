package com.example.apolloconfig.service;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ywyw2424@foxmail.com
 * @date 2019/9/17 22:52
 * @describe
 */
@Slf4j
@Service
public class ConfigService {

    @ApolloConfig
    private Config config;

    @ApolloConfig(value = "yyw.dev")
    private Config yyw_dev_config;

    public String printConfig(){
        log.info("config: {}", config);
        log.info("config getPropertyNames: {}", config.getPropertyNames());
        log.info("yyw_dev_config: {}", yyw_dev_config);
        log.info("yyw_dev_config getPropertyNames: {}", yyw_dev_config.getPropertyNames());
        return config.toString();
    }
}
