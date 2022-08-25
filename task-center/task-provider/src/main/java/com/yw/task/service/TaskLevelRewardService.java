package com.yw.task.service;

import com.yw.task.common.dto.TaskRewardDTO;
import com.yw.task.common.model.TaskLevelReward;
import com.yw.task.mapper.TaskLevelRewardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:57
 */
@Service
public class TaskLevelRewardService {

    @Resource
    private TaskLevelRewardMapper taskLevelRewardMapper;

    public List<TaskRewardDTO> get(Long taskId, Set<Integer> levels) {
        List<TaskLevelReward> taskLevelRewards = taskLevelRewardMapper.get(taskId, levels);
        return TaskRewardDTO.convert(taskLevelRewards);
    }
}
