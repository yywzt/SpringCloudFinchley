package com.yw.task.service.user;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskRewardDTO;
import com.yw.task.common.dto.user.UserTaskRewardContent;
import com.yw.task.common.enums.GrantStatusEnum;
import com.yw.task.common.model.user.UserTaskReward;
import com.yw.task.event.GrantTaskRewardEvent;
import com.yw.task.mapper.user.UserTaskRewardMapper;
import com.yw.task.service.TaskLevelRewardService;
import com.yw.task.util.JsonUtil;
import com.yyw.framework.util.IdWorkerUtil;
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
    @Resource
    private IdWorkerUtil idWorkerUtil;


    public void batchReward(Long userId, TaskDTO task, Set<Integer> levels) {
        List<TaskRewardDTO> taskLevelRewards = taskLevelRewardService.get(task.getId(), levels);
        List<UserTaskReward> userTaskRewards = saveUserTaskRewards(userId, task, taskLevelRewards);
        if (CollectionUtils.isEmpty(userTaskRewards)) {
            return;
        }
        applicationContext.publishEvent(new GrantTaskRewardEvent(this, userTaskRewards));
    }

    private List<UserTaskReward> saveUserTaskRewards(Long userId, TaskDTO task, List<TaskRewardDTO> taskLevelRewards) {
        if (CollectionUtils.isEmpty(taskLevelRewards)) {
            return Collections.emptyList();
        }
        List<UserTaskReward> userTaskRewards = taskLevelRewards.stream()
                .map(taskRewardDTO -> build(userId, task, taskRewardDTO)).collect(Collectors.toList());
        userTaskRewardMapper.batchSave(userTaskRewards);
        return userTaskRewards;
    }

    private UserTaskReward build(Long userId, TaskDTO task, TaskRewardDTO taskRewardDTO) {
        List<UserTaskRewardContent> userTaskRewardContents = taskRewardDTO.getRewards()
                .stream()
                .map(taskLevelRewardDTO -> new UserTaskRewardContent(taskLevelRewardDTO.getType(), taskLevelRewardDTO.getValue()))
                .collect(Collectors.toList());
        String rewardContent = jsonUtil.toJson(userTaskRewardContents);

        UserTaskReward userTaskReward = new UserTaskReward();
        userTaskReward.setTaskId(task.getId());
        userTaskReward.setUserId(userId);
        userTaskReward.setLevel(taskRewardDTO.getLevel());
        userTaskReward.setRewardContent(rewardContent);
        userTaskReward.setGrantStatus(GrantStatusEnum.AWAIT_GRANT.getStatus());
        userTaskReward.setTransactionNo(String.valueOf(idWorkerUtil.nextId()));
        userTaskReward.setMemo(task.getClassificationName() + ": " + task.getTitle());
        return userTaskReward;
    }

    public void grantFinished(List<UserTaskReward> userTaskRewards, GrantStatusEnum grantStatusEnum) {
        if (CollectionUtils.isEmpty(userTaskRewards)) {
            return;
        }
        List<Long> ids = userTaskRewards.stream().map(UserTaskReward::getId).collect(Collectors.toList());
        userTaskRewardMapper.updateGrantStatus(ids, grantStatusEnum.getStatus());
    }
}
