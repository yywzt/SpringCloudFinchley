package com.yw.task.service;

import com.google.common.collect.Sets;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.TaskResponseCode;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yyw.api.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 14:38
 */
public class TaskCalculationHandle {

    /**
     * 任务
     */
    private final TaskDTO task;
    /**
     * 任务等级信息
     */
    private final List<TaskLevelDTO> taskLevelList;
    /**
     * 用户当前任务进度，重置后的真实任务进度
     */
    private final UserTaskDTO userTask;
    /**
     * 新增值
     */
    private final Integer addTriggerValue;
    /**
     * 当前时间
     */
    private final LocalDateTime currentDate;

    /**
     * 用户当前任务等级
     */
    private final Integer currentLevel;

    /**
     * 取待完成的最高任务等级
     */
    private TaskLevelDTO targetFinishedTaskLevel;

    private final TaskCalculationHandleResult result;

    public TaskCalculationHandleResult getResult() {
        return result;
    }

    public TaskCalculationHandle(TaskDTO task, List<TaskLevelDTO> taskLevelList, UserTaskDTO userTask, Integer addTriggerValue) {
        this.task = task;
        this.taskLevelList = taskLevelList;
        this.userTask = userTask;
        this.addTriggerValue = addTriggerValue;
        this.currentDate = LocalDateTime.now();
        this.currentLevel = userTask.getCurrentLevel();
        this.result = TaskCalculationHandleResult.init();
        this.result.setUserTask(userTask);
    }

    public void handle() {
        if (TaskStatusEnum.FINISHED.equals(this.userTask.getTaskStatus())) {
            return;
        }
        if (isFinished()) {
            finished();
            setResult();
            return;
        }
        unFinished();
    }

    private void setResult() {
        this.result.setFinished(true);
        this.result.setFinishedDate(currentDate);
        this.result.setFinishedTaskLevels(getFinishedTaskLevels());
    }

    /**
     * 获取待完成的最高任务等级
     */
    private TaskLevelDTO getTargetFinishedTaskLevel() {
        if (Objects.isNull(this.targetFinishedTaskLevel)) {
            this.targetFinishedTaskLevel = this.taskLevelList.stream()
                    .sorted((o1, o2) -> o2.getLevel().compareTo(o1.getLevel()))
                    .filter(taskLevel -> taskLevel.getLevel() >= currentLevel)
                    .filter(taskLevel -> taskLevel.getTriggerValue() <= userTask.getTriggerValue() + addTriggerValue)
                    .findFirst()
                    .orElseGet(this::getCurrentTaskLevel);
        }
        return this.targetFinishedTaskLevel;
    }

    private TaskLevelDTO getCurrentTaskLevel() {
        return this.taskLevelList.stream()
                .filter(taskLevelDTO -> taskLevelDTO.getLevel().equals(currentLevel))
                .findFirst()
                .orElseThrow(() -> new BusinessException(TaskResponseCode.TASK_LEVEL_ERROR));
    }

    /**
     * 判断任务是否完成(触发值达标即完成)
     */
    private boolean isFinished() {
        return addTriggerValue + userTask.getTriggerValue() >= getTargetFinishedTaskLevel().getTriggerValue();
    }

    /**
     * 任务未完成，更新值
     */
    private void unFinished() {
        userTask.setTriggerValue(userTask.getTriggerValue() + addTriggerValue);
        userTask.setTaskStatus(TaskStatusEnum.UN_FINISHED);
    }

    /**
     * 任务完成，更新用户任务进度，增加任务完成记录
     */
    private void finished() {
        int newLevel = getNewLevel();
        TaskStatusEnum taskStatusEnum = getNewTaskStatusEnum();
        userTask.setCurrentLevel(newLevel);
        userTask.setTriggerValue(userTask.getTriggerValue() + addTriggerValue);
        userTask.setTaskStatus(taskStatusEnum);
    }

    private TaskStatusEnum getNewTaskStatusEnum() {
        boolean finishedAllTaskLevel = isFinishedAllTaskLevel();
        return finishedAllTaskLevel ? TaskStatusEnum.FINISHED : TaskStatusEnum.UN_FINISHED;
    }

    /**
     * 获取用户任务进度最新等级
     */
    private int getNewLevel() {
        TaskLevelDTO targetTaskLevel = getTargetFinishedTaskLevel();
        boolean finishedAllTaskLevel = isFinishedAllTaskLevel();
        return finishedAllTaskLevel ? targetTaskLevel.getLevel() : targetTaskLevel.getLevel() + 1;
    }

    /**
     * 是否完成了所有等级的任务
     */
    private boolean isFinishedAllTaskLevel() {
        TaskLevelDTO targetTaskLevel = getTargetFinishedTaskLevel();
        return targetTaskLevel.getLevel() >= task.getLevel();
    }

    private Set<Integer> getFinishedTaskLevels() {
        Integer finishedLevel = getTargetFinishedTaskLevel().getLevel();
        Set<Integer> finishedTaskLevels = Sets.newLinkedHashSet();
        for (int i = currentLevel; i <= finishedLevel; i++) {
            finishedTaskLevels.add(i);
        }
        return finishedTaskLevels;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskCalculationHandleResult {
        /**
         * 完成任务
         */
        private Boolean finished;
        private UserTaskDTO userTask;
        /**
         * 完成任务的任务等级
         */
        private Set<Integer> finishedTaskLevels;
        /**
         * 任务完成时间
         */
        private LocalDateTime finishedDate;

        public static TaskCalculationHandleResult init() {
            return new TaskCalculationHandleResult(false, null, Collections.emptySet(), null);
        }
    }
}
