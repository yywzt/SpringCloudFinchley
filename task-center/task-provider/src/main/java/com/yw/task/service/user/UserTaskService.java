package com.yw.task.service.user;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yw.task.common.constant.TaskConstant;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
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
     * @param task   任务
     * @return 用户任务
     */
    public UserTaskDTO get(Long userId, @NonNull TaskDTO task) {
        UserTask userTask = userTaskMapper.get(userId, task.getId());
        if (Objects.isNull(userTask)) {
            return defaultUserTaskDTO(userId, task.getId());
        }
        UserTaskDTO userTaskDTO = UserTaskStruct.INSTANCE.convert(userTask);
        resetUserTask(task, userTaskDTO);
        return userTaskDTO;
    }

    /**
     * 默认用户任务进度，初始状态
     */
    private UserTaskDTO defaultUserTaskDTO(Long userId, Long taskId) {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserId(userId);
        userTaskDTO.setTaskId(taskId);
        userTaskDTO.setCurrentLevel(TaskConstant.DEFAULT_INIT_LEVEL);
        userTaskDTO.setTriggerValue(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE);
        userTaskDTO.setTaskStatus(TaskConstant.DEFAULT_TASK_STATUS_ENUM);
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

    /**
     * 如果过了任务周期 则重置等级、触发值、任务状态
     */
    private void resetUserTask(TaskDTO task, UserTaskDTO userTask) {
        if (CycleTypeEnum.PERMANENT.equals(task.getCycleType())) {
            return;
        }
        if (CycleTypeEnum.WEEK.equals(task.getCycleType())) {
            if (LocalDateTimeUtil.weekOfYear(userTask.getModifyDate())
                    == LocalDateTimeUtil.weekOfYear(LocalDateTime.now())) {
                return;
            }
            doResetUserTask(userTask);
            return;
        }
        if (CycleTypeEnum.DAY.equals(task.getCycleType())) {
            if (LocalDateTimeUtil.isSameDay(userTask.getModifyDate(), LocalDateTime.now())) {
                return;
            }
            doResetUserTask(userTask);
        }
    }

    private void doResetUserTask(UserTaskDTO userTask) {
        userTask.setTriggerValue(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE);
        userTask.setCurrentLevel(TaskConstant.DEFAULT_INIT_LEVEL);
        userTask.setTaskStatus(TaskConstant.DEFAULT_TASK_STATUS_ENUM);
    }
}
