CREATE TABLE `user`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name`        VARCHAR(150)                   NOT NULL COMMENT '名称',
    `password`    VARCHAR(150)                   NOT NULL COMMENT '密码',
    `create_date` DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date` DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户表';

CREATE TABLE `user_assets`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `user_id`     BIGINT                         NOT NULL COMMENT '用户ID',
    `score`       BIGINT                         NOT NULL COMMENT '积分',
    `gold_coins`  BIGINT                         NOT NULL COMMENT '金币',
    `create_date` DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date` DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_user_id` (`user_id`),
    KEY `idx_create_date` (`create_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户资产';