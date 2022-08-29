package com.yw.user.common.model;

import com.yw.user.common.enums.AssetsChangeTypeEnum;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:35
 */
@Data
@Table(name = "user_gold_coin_record")
public class UserGoldCoinRecord {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long userId;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 变更金币
     */
    private Long goldCoin;
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
