package com.yw.user.common.api;

import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.common.vo.UserAssetsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户资产API
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 10:52
 */
public interface UserAssetsApi {

    /**
     * 获取用户资产
     *
     * @param userId 用户ID
     * @return 用户资产信息
     */
    @GetMapping(value = "/user/assets/get")
    UserAssetsVO get(@RequestParam Long userId);

    /**
     * 发放资产(积分、金币)
     *
     * @param grantAssetsRequest 参数
     */
    @PostMapping(value = "/user/assets/grant")
    Boolean grant(@RequestBody GrantAssetsRequest grantAssetsRequest);

    /**
     * 批量发放资产(积分、金币)
     *
     * @param grantAssetsRequests 参数
     */
    @PostMapping(value = "/user/assets/batchGrant")
    Boolean batchGrant(@RequestBody List<GrantAssetsRequest> grantAssetsRequests);
}
