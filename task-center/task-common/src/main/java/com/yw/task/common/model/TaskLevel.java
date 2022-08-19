package com.yw.task.common.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 17:26
 */
@Data
@Table(name = "task_level")
public class TaskLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    /**
     * 任务等级名称
     */
    private String title;

    /**
     * 任务等级描述
     */
    private String description;

    /**
     * 任务等级
     */
    private Integer level;

    /**
     * 触发值
     */
    private Integer triggerValue;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
