package com.yw.task.mapper.user;

import com.yw.task.common.model.user.UserTaskReward;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:46
 */
public interface UserTaskRewardMapper extends Mapper<UserTaskReward> {
    /**
     * 批量保存用户任务奖励
     *
     * @param userTaskRewards 用户任务奖励
     */
    void batchSave(@Param("userTaskRewards") List<UserTaskReward> userTaskRewards);

    /**
     * 更新用户任务奖励发放状态
     *
     * @param ids         任务奖励id
     * @param grantStatus 发放状态
     */
    void updateGrantStatus(@Param("ids") List<Long> ids, @Param("grantStatus") Integer grantStatus);
}
