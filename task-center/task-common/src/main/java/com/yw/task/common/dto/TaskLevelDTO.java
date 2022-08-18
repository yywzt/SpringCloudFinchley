package com.yw.task.common.dto;

import lombok.Data;

/**
 * 任务等级
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:01
 */
@Data
public class TaskLevelDTO {

    /**
     * 任务等级名称
     */
    private String title;

    /**
     * 任务等级
     */
    private Integer level;

    /**
     * 触发值
     */
    private Integer triggerValue;
}
