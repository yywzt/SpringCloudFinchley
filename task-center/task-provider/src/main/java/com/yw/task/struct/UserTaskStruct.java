package com.yw.task.struct;

import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.model.user.UserTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:25
 */
@Mapper
public interface UserTaskStruct {

    UserTaskStruct INSTANCE = Mappers.getMapper(UserTaskStruct.class);

    /**
     * UserTask -> UserTaskDTO
     *
     * @param userTask {@link UserTask}
     * @return {@link UserTaskDTO}
     */
    @Mapping(source = "level", target = "currentLevel")
    @Mapping(source = "status", target = "taskStatus")
    UserTaskDTO convert(UserTask userTask);

    /**
     * UserTaskDTO -> UserTask
     *
     * @param userTaskDTO {@link UserTaskDTO}
     * @return {@link UserTask}
     */
    @Mapping(source = "currentLevel", target = "level")
    @Mapping(source = "taskStatus.status", target = "status")
    UserTask convert(UserTaskDTO userTaskDTO);

    /**
     * 任务状态枚举映射
     *
     * @param status 任务状态
     * @return 任务状态枚举
     */
    default TaskStatusEnum customConvert(Integer status) {
        return TaskStatusEnum.findByStatus(status);
    }

}
