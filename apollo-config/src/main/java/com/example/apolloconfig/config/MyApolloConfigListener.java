package com.example.apolloconfig.config;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/10/14 10:55
 * @description
 */
@Slf4j
public class MyApolloConfigListener implements ConfigChangeListener {

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        log.info("Changes for namespace {}", changeEvent.getNamespace());
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
        }
    }

//    public void myConfigListener() {
//        //config instance is singleton for each namespace and is never null
//        Config config = ConfigService.getAppConfig();
//        config.addChangeListener(changeEvent -> {
//            log.info("Changes for namespace {}", changeEvent.getNamespace());
//            for (String key : changeEvent.changedKeys()) {
//                ConfigChange change = changeEvent.getChange(key);
//                log.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
//            }
//        });
//    }

}
