package com.yw.user.sdk.fallback;

import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.common.vo.UserAssetsVO;
import com.yw.user.sdk.client.UserAssetsClient;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 10:51
 */
public class UserAssetsClientFallback implements UserAssetsClient {
    @Override
    public UserAssetsVO get(Long userId) {
        return new UserAssetsVO(userId, 0L, 0L);
    }

    @Override
    public Boolean grant(GrantAssetsRequest grantAssetsRequest) {
        return false;
    }

    @Override
    public Boolean batchGrant(List<GrantAssetsRequest> grantAssetsRequests) {
        return false;
    }
}
