package com.yw.task.service.user;

import com.yw.task.common.constant.TaskConstant;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.model.user.UserTask;
import com.yw.task.mapper.user.UserTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/24 16:13
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class UserTaskServiceTest {

    public static final Long USER_ID = 1L;

    @Mock
    private UserTaskMapper userTaskMapper;
    @InjectMocks
    private UserTaskService userTaskService;

    public TaskDTO buildWeekTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("每周任务");
        taskDTO.setCycleType(CycleTypeEnum.WEEK);
        taskDTO.setLevel(1);
        taskDTO.setEventId("10000001");
        return taskDTO;
    }

    public TaskDTO buildDayTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(2L);
        taskDTO.setTitle("每日任务");
        taskDTO.setCycleType(CycleTypeEnum.DAY);
        taskDTO.setLevel(2);
        taskDTO.setEventId("10000002");
        return taskDTO;
    }

    public TaskDTO buildPermanentTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(3L);
        taskDTO.setTitle("永久任务");
        taskDTO.setCycleType(CycleTypeEnum.PERMANENT);
        taskDTO.setLevel(3);
        taskDTO.setEventId("10000003");
        return taskDTO;
    }

    private UserTask buildUserTask(LocalDateTime localDateTime, TaskDTO task, int level
            , int triggerValue, TaskStatusEnum taskStatusEnum) {
        UserTask userTask = new UserTask();
        userTask.setId(1L);
        userTask.setTaskId(task.getId());
        userTask.setUserId(USER_ID);
        userTask.setLevel(level);
        userTask.setTriggerValue(triggerValue);
        userTask.setStatus(taskStatusEnum.getStatus());
        userTask.setCreateDate(localDateTime);
        userTask.setModifyDate(localDateTime);
        return userTask;
    }

    //************************************************每周任务***********************************************

    /**
     * 从未做过任务
     */
    @Test
    void week_task_get_user_task_null() {
        TaskDTO task = buildWeekTask();
        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(null);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度无效、上一个周期任务未完成
     */
    @Test
    void week_task_get_user_task_invalid_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        TaskDTO task = buildWeekTask();
        UserTask userTask = buildUserTask(localDateTime, task, 1, 10, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度无效、上一个周期任务已完成
     */
    @Test
    void week_task_get_user_task_invalid_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        TaskDTO task = buildWeekTask();
        UserTask userTask = buildUserTask(localDateTime, task, 1, 10, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效、任务未完成
     */
    @Test
    void week_task_get_user_task_effective_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildWeekTask();
        UserTask userTask = buildUserTask(now, task, 1, 10, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(1, userTaskDTO.getCurrentLevel());
        assertEquals(10, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效、任务已完成
     */
    @Test
    void week_task_get_user_task_effective_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildWeekTask();
        UserTask userTask = buildUserTask(now, task, 1, 10, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(1, userTaskDTO.getCurrentLevel());
        assertEquals(10, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.FINISHED, userTaskDTO.getTaskStatus());
    }

    //************************************************每日任务***********************************************

    /**
     * 从未做过任务
     */
    @Test
    void day_task_get_user_task_null() {
        TaskDTO task = buildDayTask();
        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(null);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度无效、上一个周期任务未完成
     */
    @Test
    void day_task_get_user_task_invalid_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusDays(-1);
        TaskDTO task = buildDayTask();
        UserTask userTask = buildUserTask(localDateTime, task, 2, 20, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度无效、上一个周期任务已完成
     */
    @Test
    void day_task_get_user_task_invalid_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusDays(-1);
        TaskDTO task = buildDayTask();
        UserTask userTask = buildUserTask(localDateTime, task, 2, 20, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效、任务未完成
     */
    @Test
    void day_task_get_user_task_effective_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildDayTask();
        UserTask userTask = buildUserTask(now, task, 2, 20, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(2, userTaskDTO.getCurrentLevel());
        assertEquals(20, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效、任务已完成
     */
    @Test
    void day_task_get_user_task_effective_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildDayTask();
        UserTask userTask = buildUserTask(now, task, 2, 20, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(2, userTaskDTO.getCurrentLevel());
        assertEquals(20, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.FINISHED, userTaskDTO.getTaskStatus());
    }

    //************************************************永久任务***********************************************

    /**
     * 从未做过任务
     */
    @Test
    void permanent_task_get_user_task_null() {
        TaskDTO task = buildPermanentTask();
        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(null);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(TaskConstant.DEFAULT_INIT_LEVEL, userTaskDTO.getCurrentLevel());
        assertEquals(TaskConstant.DEFAULT_INIT_TRIGGER_VALUE, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效（上一次任务进度为前一周）任务未完成
     */
    @Test
    void permanent_task_get_user_task_invalid_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        TaskDTO task = buildPermanentTask();
        UserTask userTask = buildUserTask(localDateTime, task, 3, 30, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(3, userTaskDTO.getCurrentLevel());
        assertEquals(30, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效（上一次任务进度为前一周）任务已完成
     */
    @Test
    void permanent_task_get_user_task_invalid_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        TaskDTO task = buildPermanentTask();
        UserTask userTask = buildUserTask(localDateTime, task, 3, 30, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(3, userTaskDTO.getCurrentLevel());
        assertEquals(30, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效（上一次任务进度为当天）、任务未完成
     */
    @Test
    void permanent_task_get_user_task_effective_task_unfinished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildPermanentTask();
        UserTask userTask = buildUserTask(now, task, 3, 30, TaskStatusEnum.UN_FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(3, userTaskDTO.getCurrentLevel());
        assertEquals(30, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.UN_FINISHED, userTaskDTO.getTaskStatus());
    }

    /**
     * 任务进度有效（上一次任务进度为当天）、任务已完成
     */
    @Test
    void permanent_task_get_user_task_effective_task_finished() {
        LocalDateTime now = LocalDateTime.now();
        TaskDTO task = buildPermanentTask();
        UserTask userTask = buildUserTask(now, task, 3, 30, TaskStatusEnum.FINISHED);

        Mockito.when(userTaskMapper.get(USER_ID, task.getId())).thenReturn(userTask);
        UserTaskDTO userTaskDTO = userTaskService.get(USER_ID, task);

        assertEquals(3, userTaskDTO.getCurrentLevel());
        assertEquals(30, userTaskDTO.getTriggerValue());
        assertEquals(TaskStatusEnum.FINISHED, userTaskDTO.getTaskStatus());
    }
}