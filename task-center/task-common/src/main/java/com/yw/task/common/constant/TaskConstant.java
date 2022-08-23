package com.yw.task.common.constant;

import com.yw.task.common.enums.TaskStatusEnum;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 11:28
 */
public class TaskConstant {

    private TaskConstant() {
    }

    /**
     * 默认初始触发值
     */
    public static final Integer DEFAULT_INIT_TRIGGER_VALUE = 0;
    /**
     * 默认初始任务等级
     */
    public static final Integer DEFAULT_INIT_LEVEL = 1;
    /**
     * 默认任务状态
     */
    public static final TaskStatusEnum DEFAULT_TASK_STATUS_ENUM = TaskStatusEnum.UN_FINISHED;
}
