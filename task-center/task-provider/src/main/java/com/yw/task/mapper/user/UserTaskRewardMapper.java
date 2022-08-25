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
    void batchSave(@Param("userTaskRewards") List<UserTaskReward> userTaskRewards);
}
