package com.yw.user.common.dto.score;

import com.yw.user.common.enums.AssetsChangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:46
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreRecordDTO {

    private Long userId;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 变更积分
     */
    private Long score;
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
