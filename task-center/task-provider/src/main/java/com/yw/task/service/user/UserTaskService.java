package com.yw.task.service.user;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.model.user.UserTask;
import com.yw.task.mapper.user.UserTaskMapper;
import com.yw.task.struct.UserTaskStruct;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:59
 */
@Service
public class UserTaskService {

    @Resource
    private UserTaskMapper userTaskMapper;

    public UserTaskDTO get(Long userId, Long taskId) {
        UserTask userTask = userTaskMapper.get(userId, taskId);
        return UserTaskStruct.INSTANCE.convert(userTask);
    }

    public UserTaskDTO currentTaskLevel(Long userId, @NonNull TaskDTO task) {
        UserTaskDTO userTaskDTO = get(userId, task.getId());
        if (Objects.isNull(userTaskDTO)) {
            return buildUserTaskDTO(userId, task.getId());
        }
        return userTaskDTO;
    }

    private UserTaskDTO buildUserTaskDTO(Long userId, Long taskId) {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserId(userId);
        userTaskDTO.setTaskId(taskId);
        userTaskDTO.setCurrentLevel(1);
        userTaskDTO.setTriggerValue(0);
        userTaskDTO.setTaskStatus(TaskStatusEnum.UN_FINISHED);
        userTaskDTO.setCreateDate(LocalDateTime.now());
        return userTaskDTO;
    }
}
