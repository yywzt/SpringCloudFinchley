package com.yw.task.common.response;

import com.yw.task.common.enums.TaskStatusEnum;
import lombok.Data;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:47
 */
@Data
public class TaskLevelResponse {

    /**
     * 任务等级名称
     */
    private String title;

    /**
     * 任务等级
     */
    private Integer level;

    /**
     * 任务等级对应状态
     * {@link TaskStatusEnum}
     */
    private Integer taskStatus;
}
