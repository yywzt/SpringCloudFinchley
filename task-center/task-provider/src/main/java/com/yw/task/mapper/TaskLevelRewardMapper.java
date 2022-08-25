package com.yw.task.mapper;

import com.yw.task.common.model.TaskLevelReward;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:58
 */
public interface TaskLevelRewardMapper extends Mapper<TaskLevelReward> {
    List<TaskLevelReward> get(@Param("taskId") Long taskId, @Param("levels") Set<Integer> levels);
}
