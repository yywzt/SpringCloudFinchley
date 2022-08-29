package com.yw.user.common.dto;

import com.yw.user.common.enums.AssetsChangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 18:01
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserGoldCoinRecordDTO {

    private Long userId;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 变更积分
     */
    private Long goldCoins;
    /**
     * 备注
     */
    private String memo;
    /**
     * 流水号
     */
    private String transactionNo;

    /**
     * {@link AssetsChangeTypeEnum}
     */
    private Integer type;
}
