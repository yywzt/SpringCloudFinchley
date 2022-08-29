CREATE TABLE `user_score_record`
(
    `id`             BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `user_id`        BIGINT       NOT NULL COMMENT '用户ID',
    `balance`        BIGINT       NOT NULL COMMENT '余额',
    `score`          BIGINT       NOT NULL COMMENT '变更积分',
    `memo`           varchar(255) NOT NULL COMMENT '备注',
    `transaction_no` varchar(255) NOT NULL COMMENT '流水号',
    `type`           int          NOT NULL COMMENT '收入/支出',
    `create_date`    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '创建时间',
    `modify_date`    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) ON UPDATE CURRENT_TIMESTAMP (6) comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_transaction_no` (`transaction_no`),
    KEY              `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户积分明细';

CREATE TABLE `user_gold_coin_record`
(
    `id`             BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `user_id`        BIGINT       NOT NULL COMMENT '用户ID',
    `balance`        BIGINT       NOT NULL COMMENT '余额',
    `gold_coin`      BIGINT       NOT NULL COMMENT '变更金币',
    `memo`           varchar(255) NOT NULL COMMENT '备注',
    `transaction_no` varchar(255) NOT NULL COMMENT '流水号',
    `type`           int          NOT NULL COMMENT '收入/支出',
    `create_date`    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) COMMENT '创建时间',
    `modify_date`    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6) ON UPDATE CURRENT_TIMESTAMP (6) comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_transaction_no` (`transaction_no`),
    KEY              `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户金币明细';