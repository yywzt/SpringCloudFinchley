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

    @Mapping(source = "level", target = "currentLevel")
    @Mapping(source = "status", target = "taskStatus")
    UserTaskDTO convert(UserTask userTask);

    default TaskStatusEnum customConvert(Integer status) {
        return TaskStatusEnum.findByStatus(status);
    }

}
