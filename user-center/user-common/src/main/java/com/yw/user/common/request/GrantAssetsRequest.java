package com.yw.user.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发放积分、金币参数
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 14:33
 */
@Data
public class GrantAssetsRequest {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "积分不能为空")
    private Long score;

    @NotNull(message = "金币不能为空")
    private Long goldCoin;

    @NotBlank(message = "流水号不能为空")
    private String serialNumber;

    @NotBlank(message = "备注不能为空")
    private String memo;

}
