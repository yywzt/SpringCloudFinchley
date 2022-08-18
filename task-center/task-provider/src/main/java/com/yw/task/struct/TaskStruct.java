package com.yw.task.struct;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.model.Task;
import com.yw.task.common.response.TaskDetailsResponse;
import com.yw.task.common.response.TaskListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:25
 */
@Mapper
public interface TaskStruct {

    TaskStruct INSTANCE = Mappers.getMapper(TaskStruct.class);

    TaskDTO convert(Task task);

    List<TaskDTO> convert(List<Task> task);

    TaskDetailsResponse convert(TaskListResponse taskListResponse);

}
