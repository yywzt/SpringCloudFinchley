package com.yw.task.event;

import com.yw.task.common.dto.TaskDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 14:29
 */
@Getter
public class TaskFinishedEvent extends ApplicationEvent {

    private final Long userId;

    private final transient TaskDTO task;

    private final transient Set<Integer> finishedTaskLevels;

    private final transient LocalDateTime finishedDate;

    public TaskFinishedEvent(Object source, Long userId, TaskDTO task, Set<Integer> finishedTaskLevels, LocalDateTime finishedDate) {
        super(source);
        this.userId = userId;
        this.task = task;
        this.finishedTaskLevels = finishedTaskLevels;
        this.finishedDate = finishedDate;
    }
}
