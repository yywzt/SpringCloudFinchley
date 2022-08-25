package com.yw.task.struct;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.response.TaskLevelResponse;
import com.yw.task.common.response.TaskListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:11
 */
@Mapper
public interface TaskListResponseStruct {

    TaskListResponseStruct INSTANCE = Mappers.getMapper(TaskListResponseStruct.class);

    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "userTaskDTO.taskStatus.status", target = "taskStatus")
    @Mapping(source = "userTaskDTO.taskStatus.name", target = "taskStatusName")
    @Mapping(source = "taskLevelResponses", target = "taskLevel")
    TaskListResponse convert(TaskDTO task, UserTaskDTO userTaskDTO, List<TaskLevelResponse> taskLevelResponses);

}
