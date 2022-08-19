CREATE TABLE IF NOT EXISTS `task_classification`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name`          VARCHAR(150)                   NOT NULL COMMENT '任务分类标题',
    `sort`          INT                            NOT NULL COMMENT '排序 小->优先级高',
    `create_date`   DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date`   DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
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
    `create_date`         DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date`         DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
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
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`      BIGINT                         NOT NULL COMMENT '任务ID',
    `title`        VARCHAR(150)                   NOT NULL COMMENT '任务标题',
    `description`  VARCHAR(150)                   NOT NULL COMMENT '任务描述',
    `level`        INT                            NOT NULL COMMENT '任务等级',
    `triggerValue` INT                            NOT NULL COMMENT '触发值',
    `create_date`  DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date`  DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '任务等级';

INSERT INTO `task_level`(`task_id`, `title`, `description`, `level`, `triggerValue`)
VALUES (1, '每周累计行驶500km，任务完成', '每周累计行驶500km，任务完成', 1, 500),
       (2, '每日累计使用车机时长1小时，任务完成', '每日累计使用车机时长1小时，任务完成', 1, 3600),
       (3, '首次登录车机APP账号，可获得积分', '首次登录车机APP账号，可获得积分', 1, 1);


CREATE TABLE IF NOT EXISTS `user_task`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `task_id`      BIGINT                         NOT NULL COMMENT '任务ID',
    `user_id`      BIGINT                         NOT NULL COMMENT '用户ID',
    `level`        INT                            NOT NULL COMMENT '任务等级',
    `triggerValue` INT                            NOT NULL COMMENT '触发值',
    `status`       INT                            NOT NULL COMMENT '任务状态',
    `create_date`  DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date`  DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`)
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
    `finished_date` DATETIME                       NOT NULL COMMENT '任务完成时间',
    `create_date`   DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_date`   DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_unicode_ci
    COMMENT '用户任务记录(已完成)';