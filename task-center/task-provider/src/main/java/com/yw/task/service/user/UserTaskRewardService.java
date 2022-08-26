package com.yw.task.service.user;

import com.yw.task.common.dto.TaskRewardDTO;
import com.yw.task.common.dto.user.UserTaskRewardContent;
import com.yw.task.common.enums.GrantStatusEnum;
import com.yw.task.common.model.user.UserTaskReward;
import com.yw.task.event.GrantTaskRewardEvent;
import com.yw.task.mapper.user.UserTaskRewardMapper;
import com.yw.task.service.TaskLevelRewardService;
import com.yw.task.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:45
 */
@Service
public class UserTaskRewardService {

    @Resource
    private UserTaskRewardMapper userTaskRewardMapper;
    @Resource
    private TaskLevelRewardService taskLevelRewardService;
    @Resource
    private JsonUtil jsonUtil;
    @Resource
    private ApplicationContext applicationContext;

    public void batchReward(Long userId, Long taskId, Set<Integer> levels) {
        List<TaskRewardDTO> taskLevelRewards = taskLevelRewardService.get(taskId, levels);
        List<UserTaskReward> userTaskRewards = saveUserTaskRewards(userId, taskId, taskLevelRewards);
        applicationContext.publishEvent(new GrantTaskRewardEvent(userTaskRewards));
    }

    private List<UserTaskReward> saveUserTaskRewards(Long userId, Long taskId, List<TaskRewardDTO> taskLevelRewards) {
        if (CollectionUtils.isEmpty(taskLevelRewards)) {
            return Collections.emptyList();
        }
        List<UserTaskReward> userTaskRewards = taskLevelRewards.stream()
                .map(taskRewardDTO -> build(userId, taskId, taskRewardDTO)).collect(Collectors.toList());
        userTaskRewardMapper.batchSave(userTaskRewards);
        return userTaskRewards;
    }

    private UserTaskReward build(Long userId, Long taskId, TaskRewardDTO taskRewardDTO) {
        List<UserTaskRewardContent> userTaskRewardContents = taskRewardDTO.getRewards()
                .stream()
                .map(taskLevelRewardDTO -> new UserTaskRewardContent(taskLevelRewardDTO.getType(), taskLevelRewardDTO.getValue()))
                .collect(Collectors.toList());
        String rewardContent = jsonUtil.toJson(userTaskRewardContents);

        UserTaskReward userTaskReward = new UserTaskReward();
        userTaskReward.setTaskId(taskId);
        userTaskReward.setUserId(userId);
        userTaskReward.setLevel(taskRewardDTO.getLevel());
        userTaskReward.setRewardContent(rewardContent);
        userTaskReward.setGrantStatus(GrantStatusEnum.AWAIT_GRANT.getStatus());
        return userTaskReward;
    }

}
