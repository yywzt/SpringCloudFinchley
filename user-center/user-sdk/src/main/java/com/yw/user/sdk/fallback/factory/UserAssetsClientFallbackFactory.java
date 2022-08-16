package com.yw.user.sdk.fallback.factory;

import com.yw.user.sdk.client.UserAssetsClient;
import com.yw.user.sdk.fallback.UserAssetsClientFallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

import javax.annotation.Resource;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 10:50
 */
public class UserAssetsClientFallbackFactory implements FallbackFactory<UserAssetsClient> {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserAssetsClientFallbackFactory.class);

    @Resource
    private UserAssetsClientFallback userAssetsClientFallback;

    @Override
    public UserAssetsClient create(Throwable cause) {
        LOGGER.error("UserAssetsClientFallbackFactory", cause);
        return userAssetsClientFallback;
    }
}