package com.yw.user.controller;

import com.yw.user.common.api.UserAssetsApi;
import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.common.vo.UserAssetsVO;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 14:29
 */
@RestController
public class UserAssetsController implements UserAssetsApi {

    @Override
    public UserAssetsVO get(Long userId) {
        return null;
    }

    @Override
    public Boolean grant(GrantAssetsRequest grantAssetsRequest) {
        return null;
    }

    @Override
    public Boolean batchGrant(List<GrantAssetsRequest> grantAssetsRequests) {
        return null;
    }
}
