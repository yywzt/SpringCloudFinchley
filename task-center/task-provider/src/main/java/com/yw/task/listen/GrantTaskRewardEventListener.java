package com.yw.task.listen;

import com.yw.task.common.model.user.UserTaskReward;
import com.yw.task.event.GrantTaskRewardEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 17:15
 */
@Component
public class GrantTaskRewardEventListener {

    @EventListener(GrantTaskRewardEvent.class)
    public void grantTaskReward(GrantTaskRewardEvent grantTaskRewardEvent) {
        List<UserTaskReward> userTaskRewards = (List<UserTaskReward>) grantTaskRewardEvent.getSource();
        doGrantTaskReward(userTaskRewards);
        updateStatus(userTaskRewards);
    }

    private void updateStatus(List<UserTaskReward> userTaskRewards) {
        //TODO 发放奖励完成后 更新发放状态
    }

    private void doGrantTaskReward(List<UserTaskReward> userTaskRewards) {
        if (CollectionUtils.isEmpty(userTaskRewards)) {
            return;
        }
        for (UserTaskReward userTaskReward : userTaskRewards) {
            //TODO 发放奖励
        }
    }
}
