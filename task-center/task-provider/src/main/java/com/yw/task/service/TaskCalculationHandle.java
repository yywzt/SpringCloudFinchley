package com.yw.task.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.collect.Lists;
import com.yw.task.common.constant.TaskConstant;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
import com.yw.task.common.enums.TaskResponseCode;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.model.user.UserTaskRecord;
import com.yyw.api.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
     * 用户当前任务进度
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

    private final Integer currentLevel;

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
        resetUserTask();
        this.currentLevel = userTask.getCurrentLevel();
        this.result = TaskCalculationHandleResult.init();
    }

    public void handle() {
        if (TaskStatusEnum.FINISHED.equals(this.userTask.getTaskStatus())) {
            return;
        }
        if (isFinished()) {
            finished();
            return;
        }
        unFinished();
    }

    /**
     * 如果过了任务周期 则重置等级、触发值、任务状态
     */
    public void resetUserTask() {
        if (CycleTypeEnum.PERMANENT.equals(task.getCycleType())) {
            return;
        }
        if (CycleTypeEnum.WEEK.equals(task.getCycleType())) {
            if (LocalDateTimeUtil.weekOfYear(userTask.getModifyDate())
                    == LocalDateTimeUtil.weekOfYear(currentDate)) {
                return;
            }
            doResetUserTask();
            return;
        }
        if (CycleTypeEnum.DAY.equals(task.getCycleType())) {
            if (LocalDateTimeUtil.isSameDay(userTask.getModifyDate(), currentDate)) {
                return;
            }
            doResetUserTask();
        }
    }

    private void doResetUserTask() {
        userTask.setTriggerValue(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE);
        userTask.setCurrentLevel(TaskConstant.DEFAULT_INIT_LEVEL);
        userTask.setTaskStatus(TaskStatusEnum.UN_FINISHED);
    }

    /**
     * 获取最新任务等级
     */
    private TaskLevelDTO getNewTaskLevel() {
        return this.taskLevelList.stream()
                .sorted(Comparator.comparing(TaskLevelDTO::getLevel))
                .filter(taskLevel -> taskLevel.getLevel() >= currentLevel)
                .filter(taskLevel -> taskLevel.getTriggerValue() <= userTask.getTriggerValue() + addTriggerValue)
                .findFirst()
                .orElseGet(this::getCurrentTaskLevel);
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
        TaskLevelDTO newTaskLevel = getNewTaskLevel();
        return addTriggerValue + userTask.getTriggerValue() >= newTaskLevel.getTriggerValue();
    }

    /**
     * 任务未完成，更新值
     */
    private void unFinished() {
        userTask.setTriggerValue(userTask.getTriggerValue() + addTriggerValue);
        userTask.setTaskStatus(TaskStatusEnum.UN_FINISHED);
        this.result.setUserTask(userTask);
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
        this.result.setUserTask(userTask);

        buildUserTaskRecords(newLevel);
    }

    private TaskStatusEnum getNewTaskStatusEnum() {
        boolean finishedAllTaskLevel = isFinishedAllTaskLevel();
        return finishedAllTaskLevel ? TaskStatusEnum.FINISHED : TaskStatusEnum.UN_FINISHED;
    }

    /**
     * 获取用户任务进度最新等级
     */
    private int getNewLevel() {
        TaskLevelDTO newTaskLevel = getNewTaskLevel();
        boolean finishedAllTaskLevel = isFinishedAllTaskLevel();
        return finishedAllTaskLevel ? newTaskLevel.getLevel() : newTaskLevel.getLevel() + 1;
    }

    /**
     * 是否完成了所有等级的任务
     */
    private boolean isFinishedAllTaskLevel() {
        TaskLevelDTO newTaskLevel = getNewTaskLevel();
        return newTaskLevel.getLevel() >= taskLevelList.size();
    }

    private void buildUserTaskRecords(int newLevel) {
        List<UserTaskRecord> userTaskRecords = Lists.newArrayList();
        int level = currentLevel;
        do {
            UserTaskRecord userTaskRecord = new UserTaskRecord();
            userTaskRecord.setTaskId(userTask.getTaskId());
            userTaskRecord.setUserId(userTask.getUserId());
            userTaskRecord.setLevel(level);
            userTaskRecord.setFinishedDate(currentDate);
            userTaskRecords.add(userTaskRecord);
        } while (++level <= newLevel);

        this.result.setUserTaskRecords(userTaskRecords);
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
        private List<UserTaskRecord> userTaskRecords;

        public static TaskCalculationHandleResult init() {
            return new TaskCalculationHandleResult(false, null, Collections.emptyList());
        }
    }
}
