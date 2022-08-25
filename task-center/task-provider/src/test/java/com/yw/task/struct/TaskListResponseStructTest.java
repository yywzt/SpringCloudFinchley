package com.yw.task.struct;

import com.google.common.collect.Lists;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.response.TaskLevelResponse;
import com.yw.task.common.response.TaskListResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:13
 */
class TaskListResponseStructTest {

    @Test
    void convert() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("任务1");
        taskDTO.setLevel(1);
        taskDTO.setCycleType(CycleTypeEnum.PERMANENT);
        taskDTO.setEventId("10000001");

        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setId(2L);
        userTaskDTO.setTaskId(1L);
        userTaskDTO.setUserId(1058L);
        userTaskDTO.setCurrentLevel(1);
        userTaskDTO.setTriggerValue(10);
        userTaskDTO.setTaskStatus(TaskStatusEnum.UN_FINISHED);
        userTaskDTO.setCreateDate(LocalDateTime.now());
        userTaskDTO.setModifyDate(LocalDateTime.now());

        List<TaskLevelResponse> taskLevelResponses = Lists.newArrayList();
        TaskLevelResponse taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setTitle("任务1等级1");
        taskLevelResponse.setLevel(1);
        taskLevelResponse.setValue(10);
        taskLevelResponse.setTriggerValue(20);
        taskLevelResponse.setTaskStatus(TaskStatusEnum.UN_FINISHED.getStatus());
        taskLevelResponse.setTaskStatusName("未完成");
        taskLevelResponses.add(taskLevelResponse);

        TaskListResponse taskListResponse = TaskListResponseStruct.INSTANCE.convert(taskDTO, userTaskDTO, taskLevelResponses);
        assertEquals(taskDTO.getId(), taskListResponse.getId());
        assertEquals(taskDTO.getTitle(), taskListResponse.getTitle());
        assertEquals(taskDTO.getLevel(), taskListResponse.getLevel());
        assertEquals(userTaskDTO.getCurrentLevel(), taskListResponse.getCurrentLevel());
        assertEquals(userTaskDTO.getTaskStatus().getStatus(), taskListResponse.getTaskStatus());
        assertEquals(userTaskDTO.getTaskStatus().getName(), taskListResponse.getTaskStatusName());
        assertEquals(taskLevelResponses.size(), taskListResponse.getTaskLevel().size());
        assertEquals(taskLevelResponses, taskListResponse.getTaskLevel());
    }

}