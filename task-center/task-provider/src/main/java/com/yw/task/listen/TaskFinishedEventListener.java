package com.yw.task.listen;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.event.TaskFinishedEvent;
import com.yw.task.service.user.UserTaskRecordService;
import com.yw.task.service.user.UserTaskRewardService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 任务完成后处理
 * 1、保存用户任务完成记录
 * 2、保存用户任务完成奖励记录
 *
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
        Long userId = taskFinishedEvent.getUserId();
        TaskDTO task = taskFinishedEvent.getTask();
        Set<Integer> finishedTaskLevels = taskFinishedEvent.getFinishedTaskLevels();
        LocalDateTime finishedDate = taskFinishedEvent.getFinishedDate();

        addUserTaskRecord(userId, task.getId(), finishedTaskLevels, finishedDate);
        addUserTaskReward(userId, task.getId(), finishedTaskLevels);
    }

    private void addUserTaskReward(Long userId, Long taskId, Set<Integer> finishedTaskLevelSet) {
        userTaskRewardService.batchReward(userId, taskId, finishedTaskLevelSet);
    }

    private void addUserTaskRecord(Long userId, Long taskId, Set<Integer> finishedTaskLevels, LocalDateTime finishedDate) {
        userTaskRecordService.batchRecord(finishedTaskLevels, userId, taskId, finishedDate);
    }
}
