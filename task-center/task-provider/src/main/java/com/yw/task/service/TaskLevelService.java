package com.yw.task.service;

import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.model.TaskLevel;
import com.yw.task.mapper.TasklevelMapper;
import com.yw.task.struct.TaskLevelStruct;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:59
 */
@Service
public class TaskLevelService {

    @Resource
    private TasklevelMapper tasklevelMapper;

    public List<TaskLevelDTO> list(Long taskId) {
        List<TaskLevel> taskLevelList = tasklevelMapper.list(taskId);
        return TaskLevelStruct.INSTANCE.convert(taskLevelList);
    }
}
