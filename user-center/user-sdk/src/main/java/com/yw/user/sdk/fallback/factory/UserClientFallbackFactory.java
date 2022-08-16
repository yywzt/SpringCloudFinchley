package com.yw.user.sdk.fallback.factory;

import com.yw.user.sdk.client.UserClient;
import com.yw.user.sdk.fallback.UserClientFallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

import javax.annotation.Resource;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:56
 */
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    @Resource
    private UserClientFallback userClientFallback;

    @Override
    public UserClient create(Throwable cause) {
        LOGGER.error("UserClientFallbackFactory", cause);
        return userClientFallback;
    }
}
