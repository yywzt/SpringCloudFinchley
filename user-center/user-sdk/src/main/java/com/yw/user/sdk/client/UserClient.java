package com.yw.user.sdk.client;

import com.yw.user.common.api.UserApi;
import com.yw.user.sdk.fallback.factory.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:51
 */
@FeignClient(value = "service-user", contextId = "userClient"
        , path = "service-user", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient extends UserApi {
}
