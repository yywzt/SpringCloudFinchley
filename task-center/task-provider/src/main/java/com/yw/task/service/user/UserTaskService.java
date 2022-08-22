package com.yw.task.service.user;

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

    /**
     * 获取用户任务进度，如从未做过任务，则返回初始任务进度
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     * @return 用户任务
     */
    public UserTaskDTO get(Long userId, @NonNull Long taskId) {
        UserTask userTask = userTaskMapper.get(userId, taskId);
        if (Objects.isNull(userTask)) {
            return buildUserTaskDTO(userId, taskId);
        }
        return UserTaskStruct.INSTANCE.convert(userTask);
    }

    private UserTaskDTO buildUserTaskDTO(Long userId, Long taskId) {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserId(userId);
        userTaskDTO.setTaskId(taskId);
        userTaskDTO.setCurrentLevel(1);
        userTaskDTO.setTriggerValue(0);
        userTaskDTO.setTaskStatus(TaskStatusEnum.UN_FINISHED);
        userTaskDTO.setCreateDate(LocalDateTime.now());
        userTaskDTO.setModifyDate(LocalDateTime.now());
        return userTaskDTO;
    }

    public void addOrUpdate(UserTaskDTO userTaskDTO) {
        if (Objects.isNull(userTaskDTO)) {
            return;
        }
        UserTask userTask = UserTaskStruct.INSTANCE.convert(userTaskDTO);
        userTaskMapper.addOrUpdate(userTask);
    }
}
