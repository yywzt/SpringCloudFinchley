package com.yw.user.controller;

import com.yw.user.common.api.UserAssetsApi;
import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.common.vo.UserAssetsVO;
import com.yw.user.service.UserAssetsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 14:29
 */
@Validated
@RestController
public class UserAssetsController implements UserAssetsApi {

    @Resource
    private UserAssetsService userAssetsService;

    @Override
    public UserAssetsVO get(@RequestParam Long userId) {
        return userAssetsService.getByUserId(userId);
    }

    @Override
    public Boolean grant(@Valid @RequestBody GrantAssetsRequest grantAssetsRequest) {
        userAssetsService.grant(grantAssetsRequest);
        return true;
    }

    @Override
    public Boolean batchGrant(@RequestBody @NotEmpty(message = "参数为空") List<@Valid GrantAssetsRequest> grantAssetsRequests) {
        userAssetsService.batchGrant(grantAssetsRequests);
        return true;
    }
}
