package com.yw.task.event;

import com.yw.task.common.model.user.UserTaskReward;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 发放任务奖励
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 17:15
 */
@Getter
public class GrantTaskRewardEvent extends ApplicationEvent {

    private final transient List<UserTaskReward> userTaskRewards;

    public GrantTaskRewardEvent(Object source, List<UserTaskReward> userTaskRewards) {
        super(source);
        this.userTaskRewards = userTaskRewards;
    }
}
