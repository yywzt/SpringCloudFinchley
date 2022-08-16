package com.yw.user.sdk.client;

import com.yw.user.common.api.UserAssetsApi;
import com.yw.user.sdk.fallback.factory.UserAssetsClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 10:50
 */
@FeignClient(value = "service-user", contextId = "userAssetsClient"
        , path = "service-user", fallbackFactory = UserAssetsClientFallbackFactory.class)
public interface UserAssetsClient extends UserAssetsApi {

}
