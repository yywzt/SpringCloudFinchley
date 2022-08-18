package com.yw.task.struct;

import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.model.TaskLevel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:11
 */
@Mapper
public interface TaskLevelStruct {

    TaskLevelStruct INSTANCE = Mappers.getMapper(TaskLevelStruct.class);

    TaskLevelDTO convert(TaskLevel taskLevel);

    List<TaskLevelDTO> convert(List<TaskLevel> taskLevels);
}
