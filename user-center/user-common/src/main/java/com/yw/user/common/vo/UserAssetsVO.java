package com.yw.user.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 14:31
 */
@Data
@AllArgsConstructor
public class UserAssetsVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 积分
     */
    private Long score;

    /**
     * 金币
     */
    private Long goldCoins;

}
