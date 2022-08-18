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

CREATE TABLE IF NOT EXISTS `task_level`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
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