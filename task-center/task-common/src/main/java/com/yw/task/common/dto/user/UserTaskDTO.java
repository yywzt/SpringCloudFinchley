package com.yw.task.common.dto.user;

import com.yw.task.common.enums.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户任务信息
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:59
 */
@Data
public class UserTaskDTO {

    private Long id;

    private Long taskId;

    private Long userId;

    /**
     * 当前任务等级
     */
    private Integer currentLevel;

    /**
     * 当前触发值
     */
    private Integer triggerValue;

    /**
     * 任务状态
     * {@link TaskStatusEnum}
     */
    private TaskStatusEnum taskStatus;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
