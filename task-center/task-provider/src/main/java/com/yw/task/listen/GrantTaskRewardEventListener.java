package com.yw.task.listen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yw.task.common.dto.user.UserTaskRewardContent;
import com.yw.task.common.enums.TaskRewardTypeEnum;
import com.yw.task.common.model.user.UserTaskReward;
import com.yw.task.event.GrantTaskRewardEvent;
import com.yw.task.service.user.UserTaskRewardService;
import com.yw.task.util.JsonUtil;
import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.sdk.client.UserAssetsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 17:15
 */
@Slf4j
@Component
public class GrantTaskRewardEventListener {

    @Resource
    private UserTaskRewardService userTaskRewardService;
    @Resource
    private UserAssetsClient userAssetsClient;
    @Resource
    private JsonUtil jsonUtil;

    @Async("grantTaskRewardExecutor")
    @EventListener(GrantTaskRewardEvent.class)
    public void grantTaskReward(GrantTaskRewardEvent grantTaskRewardEvent) {
        List<UserTaskReward> userTaskRewards = grantTaskRewardEvent.getUserTaskRewards();
        doGrantTaskReward(userTaskRewards);
        updateGrantStatus(userTaskRewards);
    }

    private void updateGrantStatus(List<UserTaskReward> userTaskRewards) {
        userTaskRewardService.grantFinished(userTaskRewards);
    }

    private void doGrantTaskReward(List<UserTaskReward> userTaskRewards) {
        if (CollectionUtils.isEmpty(userTaskRewards)) {
            return;
        }
        List<GrantAssetsRequest> grantAssetsRequests = userTaskRewards.stream()
                .map(this::build)
                .collect(Collectors.toList());
        Boolean result = userAssetsClient.batchGrant(grantAssetsRequests);
        log.info("doGrantTaskReward execute result: {}", result);
    }

    private GrantAssetsRequest build(UserTaskReward userTaskReward) {
        String rewardContent = userTaskReward.getRewardContent();
        List<UserTaskRewardContent> userTaskRewardContents = jsonUtil.fromJson(rewardContent, new TypeReference<List<UserTaskRewardContent>>() {
        });
        Long score = userTaskRewardContents.stream()
                .filter(rc -> rc.getType().equals(TaskRewardTypeEnum.SCORE.getType()))
                .mapToLong(UserTaskRewardContent::getValue)
                .findFirst()
                .orElse(0L);
        Long goldCoin = userTaskRewardContents.stream()
                .filter(rc -> rc.getType().equals(TaskRewardTypeEnum.GOLD_COIN.getType()))
                .mapToLong(UserTaskRewardContent::getValue)
                .findFirst()
                .orElse(0L);
        String serialNumber = userTaskReward.getSerialNumber();

        GrantAssetsRequest grantAssetsRequest = new GrantAssetsRequest();
        grantAssetsRequest.setUserId(userTaskReward.getUserId());
        grantAssetsRequest.setScore(score);
        grantAssetsRequest.setGoldCoin(goldCoin);
        grantAssetsRequest.setSerialNumber(serialNumber);
        grantAssetsRequest.setMemo(userTaskReward.getMemo());
        return grantAssetsRequest;
    }
}
