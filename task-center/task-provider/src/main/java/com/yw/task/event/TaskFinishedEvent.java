package com.yw.task.event;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.model.user.UserTaskRecord;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 14:29
 */
@Getter
public class TaskFinishedEvent extends ApplicationEvent {

    private final transient TaskDTO task;

    private final transient List<UserTaskRecord> userTaskRecords;

    public TaskFinishedEvent(Object source, TaskDTO task, List<UserTaskRecord> userTaskRecords) {
        super(source);
        this.task = task;
        this.userTaskRecords = userTaskRecords;
    }
}
