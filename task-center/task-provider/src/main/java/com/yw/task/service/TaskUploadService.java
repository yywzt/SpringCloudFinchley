package com.yw.task.service;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.request.TaskUploadRequest;
import com.yw.task.service.user.UserTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private UserTaskRecordService userTaskRecordService;

    public void upload(TaskUploadRequest taskUploadRequest) {
        TaskDTO task = taskService.getByEventId(taskUploadRequest.getEventId());
        List<TaskLevelDTO> taskLevelList = taskLevelService.list(task.getId());
        UserTaskDTO userTask = userTaskService.get(taskUploadRequest.getUserId(), task.getId());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask
                , taskUploadRequest.getTriggerValue());
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        userTaskService.addOrUpdate(result.getUserTask());
        userTaskRecordService.batchAdd(result.getUserTaskRecords());
    }
}
