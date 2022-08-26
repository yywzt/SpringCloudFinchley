package com.yw.task.service;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.request.TaskUploadRequest;
import com.yw.task.event.TaskFinishedEvent;
import com.yw.task.service.user.UserTaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 16:05
 */
@Service
public class TaskUploadService {

    @Resource
    private UserTaskService userTaskService;
    @Resource
    private TaskService taskService;
    @Resource
    private TaskLevelService taskLevelService;
    @Lazy
    @Resource
    private TaskUploadService proxyTaskUploadService;
    @Resource
    private ApplicationContext applicationContext;

    public void upload(TaskUploadRequest taskUploadRequest) {
        TaskDTO task = taskService.getByEventId(taskUploadRequest.getEventId());
        List<TaskLevelDTO> taskLevelList = taskLevelService.list(task.getId());
        UserTaskDTO userTask = userTaskService.get(taskUploadRequest.getUserId(), task);

        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask
                , taskUploadRequest.getTriggerValue());
        taskCalculationHandle.handle();

        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        proxyTaskUploadService.uploadAfterHandle(taskUploadRequest.getUserId(), task, result);
    }

    @Transactional(rollbackFor = Exception.class)
    public void uploadAfterHandle(@NotNull Long userId, TaskDTO task, TaskCalculationHandle.TaskCalculationHandleResult result) {
        userTaskService.addOrUpdate(result.getUserTask());
        if (Boolean.FALSE.equals(result.getFinished())) {
            return;
        }
        applicationContext.publishEvent(new TaskFinishedEvent(userId, task, result.getFinishedTaskLevels(), result.getFinishedDate()));
    }
}
