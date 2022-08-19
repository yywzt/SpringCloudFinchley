package com.yw.task.common.response;

import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import lombok.Data;
import lombok.NonNull;

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
     * 用户当前值
     */
    private Integer value;

    /**
     * 触发值
     */
    private Integer triggerValue;

    /**
     * 任务等级对应状态
     * {@link TaskStatusEnum}
     */
    private Integer taskStatus;

    private String taskStatusName;

    public TaskLevelResponse() {
    }

    public TaskLevelResponse(@NonNull TaskLevelDTO taskLevel, @NonNull UserTaskDTO userTaskDTO) {
        this.title = taskLevel.getTitle();
        this.level = taskLevel.getLevel();
        this.value = userTaskDTO.getTriggerValue();
        this.triggerValue = taskLevel.getTriggerValue();
        TaskStatusEnum taskStatusEnum = buildTaskLevelTaskStatus(taskLevel, userTaskDTO);
        this.taskStatus = taskStatusEnum.getStatus();
        this.taskStatusName = taskStatusEnum.getName();
    }

    private TaskStatusEnum buildTaskLevelTaskStatus(@NonNull TaskLevelDTO taskLevel, @NonNull UserTaskDTO userTaskDTO) {
        if (taskLevel.getLevel().equals(userTaskDTO.getCurrentLevel())) {
            return userTaskDTO.getTaskStatus();
        }
        if (taskLevel.getLevel() < userTaskDTO.getCurrentLevel()) {
            return TaskStatusEnum.FINISHED;
        }
        if (taskLevel.getLevel() > userTaskDTO.getCurrentLevel()) {
            return TaskStatusEnum.NO_STARTED;
        }
        return TaskStatusEnum.UN_FINISHED;
    }

}
