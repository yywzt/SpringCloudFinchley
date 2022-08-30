CREATE TABLE IF NOT EXISTS `task_classification`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name`          VARCHAR(150)                   NOT NULL COMMENT '任务分类标题',
    `sort`          INT                            NOT NULL COMMENT '排序 小->优先级高',
    `create_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    `enable_status` TINYINT                        NOT NULL DEFAULT 0 COMMENT '有效标识',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '任务分类';

INSERT INTO `task_classification`(`name`, `sort`, `enable_status`)
VALUES ('每日任务', 2, 1),
       ('每周任务', 3, 1),
       ('新手任务', 1, 1);


CREATE TABLE IF NOT EXISTS `task`
(
    `id`                  BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `title`               VARCHAR(150)                   NOT NULL COMMENT '任务标题',
    `description`         VARCHAR(150)                   NOT NULL COMMENT '任务描述',
    `classification_id`   BIGINT                         NOT NULL COMMENT '分类ID',
    `classification_name` VARCHAR(150)                   NOT NULL COMMENT '分类名称',
    `level`               INT                            NOT NULL COMMENT '任务总等级',
    `event_id`            VARCHAR(150)                   NOT NULL COMMENT '任务对应事件ID',
    `cycle_type`          INT                            NOT NULL COMMENT '任务周期类型',
    `create_date`         DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`         DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    `enable_status`       TINYINT                        NOT NULL DEFAULT 0 COMMENT '有效标识',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '任务';

INSERT INTO `task`(`title`, `description`, `classification_id`, `classification_name`, `level`, `event_id`,
                   `cycle_type`, `enable_status`)
VALUES ('车辆使用任务-里程', '车辆使用任务-里程', 2, '每周任务', 1, '10000001', 3, 1),
       ('车辆使用任务-时长', '车辆使用任务-时长', 1, '每日任务', 1, '10000002', 2, 1),
       ('首次登录车主账号', '首次登录车主账号', 3, '新手任务', 1, '10000003', 1, 1);


CREATE TABLE IF NOT EXISTS `task_level`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`       BIGINT                         NOT NULL COMMENT '任务ID',
    `title`         VARCHAR(150)                   NOT NULL COMMENT '任务标题',
    `description`   VARCHAR(150)                   NOT NULL COMMENT '任务描述',
    `level`         INT                            NOT NULL COMMENT '任务等级',
    `trigger_value` INT                            NOT NULL COMMENT '触发值',
    `create_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '任务等级';

INSERT INTO `task_level`(`task_id`, `title`, `description`, `level`, `trigger_value`)
VALUES (1, '每周累计行驶500km，任务完成', '每周累计行驶500km，任务完成', 1, 500),
       (2, '每日累计使用车机时长1小时，任务完成', '每日累计使用车机时长1小时，任务完成', 1, 3600),
       (3, '首次登录车机APP账号，可获得积分', '首次登录车机APP账号，可获得积分', 1, 1);

CREATE TABLE IF NOT EXISTS `task_level_reward`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`     BIGINT                         NOT NULL COMMENT '任务ID',
    `level`       INT                            NOT NULL COMMENT '任务等级',
    `type`        INT                            NOT NULL COMMENT '奖励类型',
    `value`       INT                            NOT NULL COMMENT '奖励数值',
    `create_date` DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date` DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_task_id_level_type` (`task_id`, `level`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '任务等级奖励';

INSERT INTO `task_level_reward`(`task_id`, `level`, `type`, `value`)
VALUES (1, 1, 1, 100),
       (2, 1, 1, 100),
       (3, 1, 1, 100);


CREATE TABLE IF NOT EXISTS `user_task`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`       BIGINT                         NOT NULL COMMENT '任务ID',
    `user_id`       BIGINT                         NOT NULL COMMENT '用户ID',
    `level`         INT                            NOT NULL COMMENT '任务等级',
    `trigger_value` INT                            NOT NULL COMMENT '触发值',
    `status`        INT                            NOT NULL COMMENT '任务状态',
    `create_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_user_id_task_id` (`user_id`, `task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户任务进度';

CREATE TABLE IF NOT EXISTS `user_task_record`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`       BIGINT                         NOT NULL COMMENT '任务ID',
    `user_id`       BIGINT                         NOT NULL COMMENT '用户ID',
    `level`         INT                            NOT NULL COMMENT '任务等级',
    `finished_date` DATETIME(6)                    NOT NULL COMMENT '任务完成时间',
    `create_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`   DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户任务记录(已完成)';

CREATE TABLE IF NOT EXISTS `user_task_reward`
(
    `id`             BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`        BIGINT                         NOT NULL COMMENT '任务ID',
    `user_id`        BIGINT                         NOT NULL COMMENT '用户ID',
    `level`          INT                            NOT NULL COMMENT '任务等级',
    `reward_content` text                           NOT NULL COMMENT '奖励数据',
    `grant_status`   INT                            NOT NULL COMMENT '发放状态',
    `transaction_no` VARCHAR(20)                    NOT NULL COMMENT '流水号',
    `memo`           VARCHAR(255)                   NOT NULL COMMENT '备注',
    `create_date`    DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `modify_date`    DATETIME(6)                    NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户任务完成奖励记录';