package com.yw.task.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskResponseCode;
import com.yw.task.common.model.Task;
import com.yw.task.common.request.TaskDetailsRequest;
import com.yw.task.common.request.TaskListRequest;
import com.yw.task.common.response.TaskDetailsResponse;
import com.yw.task.common.response.TaskLevelResponse;
import com.yw.task.common.response.TaskListResponse;
import com.yw.task.mapper.TaskMapper;
import com.yw.task.service.user.UserTaskService;
import com.yw.task.struct.TaskStruct;
import com.yw.task.util.PageUtil;
import com.yyw.api.enums.EnableStatusEnum;
import com.yyw.api.exception.BusinessException;
import com.yyw.api.vo.PageInfoVO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:12
 */
@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private UserTaskService userTaskService;
    @Resource
    private TaskLevelService taskLevelService;

    public TaskDTO get(Long id) {
        Optional<Task> taskOptional = taskMapper.get(id, EnableStatusEnum.ENABLE_STATUS.getCode());
        Task task = taskOptional
                .orElseThrow(() -> new BusinessException(TaskResponseCode.TASK_NOT_EXIST));
        return TaskStruct.INSTANCE.convert(task);
    }

    public PageInfoVO<Task> page(Long classificationId, int page, int size) {
        PageInfo<Task> taskPageInfo = PageMethod.startPage(page, size)
                .doSelectPageInfo(() -> taskMapper.list(classificationId, EnableStatusEnum.ENABLE_STATUS.getCode()));
        return PageUtil.INSTANCE.convert(taskPageInfo, page, size);
    }

    public TaskDTO getByEventId(String eventId) {
        Optional<Task> taskOptional = taskMapper.findByEventId(eventId, EnableStatusEnum.ENABLE_STATUS.getCode());
        Task task = taskOptional
                .orElseThrow(() -> new BusinessException(TaskResponseCode.TASK_NOT_EXIST));
        return TaskStruct.INSTANCE.convert(task);
    }

    public PageInfoVO<TaskListResponse> list(TaskListRequest taskListRequest) {
        PageInfoVO<Task> taskPageInfo = page(taskListRequest.getClassificationId(), taskListRequest.getPage(), taskListRequest.getSize());
        List<TaskDTO> taskList = TaskStruct.INSTANCE.convert(taskPageInfo.getList());
        List<TaskListResponse> taskListResponses = taskList.stream()
                .map(task -> buildTaskListResponse(taskListRequest.getUserId(), task))
                .collect(Collectors.toList());
        return taskPageInfo.build(taskListResponses);
    }

    private TaskListResponse buildTaskListResponse(@NonNull Long userId, TaskDTO task) {
        List<TaskLevelDTO> taskLevelList = taskLevelService.list(task.getId());
        UserTaskDTO userTaskDTO = userTaskService.get(userId, task);
        List<TaskLevelResponse> taskLevelResponses = taskLevelList.stream()
                .map(taskLevel -> new TaskLevelResponse(taskLevel, userTaskDTO))
                .collect(Collectors.toList());

        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setId(task.getId());
        taskListResponse.setTitle(task.getTitle());
        taskListResponse.setLevel(task.getLevel());
        taskListResponse.setCurrentLevel(userTaskDTO.getCurrentLevel());
        taskListResponse.setTaskStatus(userTaskDTO.getTaskStatus().getStatus());
        taskListResponse.setTaskStatusName(userTaskDTO.getTaskStatus().getName());
        taskListResponse.setTaskLevel(taskLevelResponses);
        return taskListResponse;
    }

    public TaskDetailsResponse details(TaskDetailsRequest taskDetailsRequest) {
        TaskDTO taskDTO = get(taskDetailsRequest.getTaskId());
        TaskListResponse taskListResponse = buildTaskListResponse(taskDetailsRequest.getUserId(), taskDTO);
        return TaskStruct.INSTANCE.convert(taskListResponse);
    }
}
