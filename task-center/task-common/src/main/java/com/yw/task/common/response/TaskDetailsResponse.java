package com.yw.task.common.response;

import com.yw.task.common.enums.TaskStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * 任务详情
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:56
 */
@Data
public class TaskDetailsResponse {

    private Long id;

    /**
     * 任务名称
     */
    private String title;

    /**
     * 任务总等级
     */
    private Integer level;

    /**
     * 当前任务等级
     */
    private Integer currentLevel;

    /**
     * 任务状态
     * {@link TaskStatusEnum}
     */
    private Integer taskStatus;

    private String taskStatusName;

    /**
     * 任务等级
     */
    private List<TaskLevelResponse> taskLevel;

}
