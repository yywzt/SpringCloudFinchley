package com.yw.task.struct;

import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.response.TaskLevelResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/30 11:02
 */
class TaskLevelResponseStructTest {

    @Test
    void convert() {
        TaskLevelDTO taskLevel = new TaskLevelDTO();
        taskLevel.setTitle("任务等级1");
        taskLevel.setLevel(1);
        taskLevel.setTriggerValue(10);

        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setId(2L);
        userTaskDTO.setTaskId(1L);
        userTaskDTO.setUserId(1058L);
        userTaskDTO.setCurrentLevel(1);
        userTaskDTO.setTriggerValue(0);
        userTaskDTO.setTaskStatus(TaskStatusEnum.UN_FINISHED);
        userTaskDTO.setCreateDate(LocalDateTime.now());
        userTaskDTO.setModifyDate(LocalDateTime.now());

        TaskStatusEnum taskStatusEnum = TaskStatusEnum.UN_FINISHED;

        TaskLevelResponse taskLevelResponse = TaskLevelResponseStruct.INSTANCE.convert(taskLevel, userTaskDTO, taskStatusEnum);


        assertNotNull(taskLevelResponse);
        assertEquals(taskLevel.getTitle(), taskLevelResponse.getTitle());
        assertEquals(taskLevel.getLevel(), taskLevelResponse.getLevel());
        assertEquals(userTaskDTO.getTriggerValue(), taskLevelResponse.getValue());
        assertEquals(taskLevel.getTriggerValue(), taskLevelResponse.getTriggerValue());
        assertEquals(taskStatusEnum.getStatus(), taskLevelResponse.getTaskStatus());
        assertEquals(taskStatusEnum.getName(), taskLevelResponse.getTaskStatusName());
    }
}