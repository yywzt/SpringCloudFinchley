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
    /**
     * 查询任务等级奖励
     *
     * @param taskId 任务ID
     * @param levels 任务等级集合
     * @return 任务等级奖励
     */
    List<TaskLevelReward> get(@Param("taskId") Long taskId, @Param("levels") Set<Integer> levels);
}
