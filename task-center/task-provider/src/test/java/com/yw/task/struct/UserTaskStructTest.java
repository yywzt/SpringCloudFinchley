package com.yw.task.struct;

import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.model.user.UserTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 10:22
 */
class UserTaskStructTest {

    @Test
    void user_task_convert_user_task_dto() {
        UserTask userTask = new UserTask();
        userTask.setTriggerValue(100);
        userTask.setLevel(1);
        userTask.setStatus(TaskStatusEnum.FINISHED.getStatus());
        UserTaskDTO userTaskDTO = UserTaskStruct.INSTANCE.convert(userTask);

        assertEquals(userTask.getTriggerValue(), userTaskDTO.getTriggerValue());
        assertEquals(userTask.getLevel(), userTaskDTO.getCurrentLevel());
        assertEquals(userTask.getStatus(), userTaskDTO.getTaskStatus().getStatus());
    }

    @Test
    void user_task_dto_convert_user_task() {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setTriggerValue(100);
        userTaskDTO.setCurrentLevel(1);
        userTaskDTO.setTaskStatus(TaskStatusEnum.FINISHED);
        UserTask userTask = UserTaskStruct.INSTANCE.convert(userTaskDTO);

        assertEquals(userTaskDTO.getTriggerValue(), userTask.getTriggerValue());
        assertEquals(userTaskDTO.getCurrentLevel(), userTask.getLevel());
        assertEquals(userTaskDTO.getTaskStatus().getStatus(), userTask.getStatus());
    }
}