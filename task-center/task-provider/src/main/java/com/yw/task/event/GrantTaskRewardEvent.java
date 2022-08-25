package com.yw.task.event;

import com.yw.task.common.model.user.UserTaskReward;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 发放任务奖励
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 17:15
 */
public class GrantTaskRewardEvent extends ApplicationEvent {

    public GrantTaskRewardEvent(List<UserTaskReward> userTaskRewards) {
        super(userTaskRewards);
    }
}
