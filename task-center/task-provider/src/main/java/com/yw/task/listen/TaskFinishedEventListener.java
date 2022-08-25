package com.yw.task.listen;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.model.user.UserTaskRecord;
import com.yw.task.event.TaskFinishedEvent;
import com.yw.task.service.user.UserTaskRecordService;
import com.yw.task.service.user.UserTaskRewardService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 14:39
 */
@Component
public class TaskFinishedEventListener {

    @Resource
    private UserTaskRecordService userTaskRecordService;
    @Resource
    private UserTaskRewardService userTaskRewardService;

    @EventListener(TaskFinishedEvent.class)
    public void taskFinished(TaskFinishedEvent taskFinishedEvent) {
        Long userId = (Long) taskFinishedEvent.getSource();
        TaskDTO task = taskFinishedEvent.getTask();
        List<UserTaskRecord> userTaskRecords = taskFinishedEvent.getUserTaskRecords();

        addUserTaskRecord(userTaskRecords);
        addUserTaskReward(userId, task, userTaskRecords);
    }

    private void addUserTaskReward(Long userId, TaskDTO task, List<UserTaskRecord> userTaskRecords) {
        Set<Integer> finishedTaskLevelSet = userTaskRecords.stream()
                .map(UserTaskRecord::getLevel)
                .collect(Collectors.toSet());

        userTaskRewardService.add(userId, task.getId(), finishedTaskLevelSet);
    }

    private void addUserTaskRecord(List<UserTaskRecord> userTaskRecords) {
        userTaskRecordService.batchAdd(userTaskRecords);
    }
}
