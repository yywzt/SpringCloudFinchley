package com.yw.task.struct;

import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.response.TaskLevelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/30 11:00
 */
@Mapper
public interface TaskLevelResponseStruct {

    TaskLevelResponseStruct INSTANCE = Mappers.getMapper(TaskLevelResponseStruct.class);

    /**
     * TaskLevelDTO + UserTaskDTO + TaskStatusEnum -> TaskLevelResponse
     *
     * @param taskLevel      {@link TaskLevelDTO}
     * @param userTaskDTO    {@link UserTaskDTO}
     * @param taskStatusEnum {@link TaskStatusEnum}
     * @return {@link TaskLevelResponse}
     */
    @Mapping(source = "taskLevel.level", target = "level")
    @Mapping(source = "userTaskDTO.triggerValue", target = "value")
    @Mapping(source = "taskLevel.triggerValue", target = "triggerValue")
    @Mapping(source = "taskStatusEnum.status", target = "taskStatus")
    @Mapping(source = "taskStatusEnum.name", target = "taskStatusName")
    TaskLevelResponse convert(TaskLevelDTO taskLevel, UserTaskDTO userTaskDTO, TaskStatusEnum taskStatusEnum);

}
