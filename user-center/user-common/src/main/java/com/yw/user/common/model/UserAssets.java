package com.yw.user.common.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:22
 */
@Data
@Table(name = "user_assets")
public class UserAssets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
