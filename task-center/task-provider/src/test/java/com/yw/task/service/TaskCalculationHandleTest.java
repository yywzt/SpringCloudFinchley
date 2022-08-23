package com.yw.task.service;

import com.google.common.collect.Lists;
import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.dto.TaskLevelDTO;
import com.yw.task.common.dto.user.UserTaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
import com.yw.task.common.enums.TaskStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 1、任务已全部完成
 * 2、从未做过任务
 * 3、任务未完成 user_task不为空且触发值有效
 * 4、任务未完成 user_task不为空且触发值无效
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 15:34
 */
class TaskCalculationHandleTest {

    public static final Long USER_ID = 1L;

    private UserTaskDTO buildUserTask(Long id, Long taskId, Integer currentLevel, Integer triggerValue
            , TaskStatusEnum taskStatusEnum, LocalDateTime createDate, LocalDateTime modifyDate) {
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setId(id);
        userTaskDTO.setUserId(TaskCalculationHandleTest.USER_ID);
        userTaskDTO.setTaskId(taskId);
        userTaskDTO.setCurrentLevel(currentLevel);
        userTaskDTO.setTriggerValue(triggerValue);
        userTaskDTO.setTaskStatus(taskStatusEnum);
        userTaskDTO.setCreateDate(createDate);
        userTaskDTO.setModifyDate(modifyDate);
        return userTaskDTO;
    }

    /**
     * 构造任务等级数据
     *
     * @param content title,level,triggerValue;title,level,triggerValue;...
     */
    private List<TaskLevelDTO> buildTaskLevels(String content) {
        List<TaskLevelDTO> taskLevelList = Lists.newArrayList();
        for (String s : StringUtils.split(content, ";")) {
            String[] taskLevelArray = StringUtils.split(s, ",");
            TaskLevelDTO taskLevelDTO = new TaskLevelDTO();
            taskLevelDTO.setTitle(taskLevelArray[0]);
            taskLevelDTO.setLevel(Integer.valueOf(taskLevelArray[1]));
            taskLevelDTO.setTriggerValue(Integer.valueOf(taskLevelArray[2]));
            taskLevelList.add(taskLevelDTO);
        }
        return taskLevelList;
    }

    private TaskDTO buildTaskDTO(Long id, String title, Integer level, CycleTypeEnum cycleTypeEnum, String eventId) {
        TaskDTO task = new TaskDTO();
        task.setId(id);
        task.setTitle(title);
        task.setLevel(level);
        task.setCycleType(cycleTypeEnum);
        task.setEventId(eventId);
        return task;
    }

    /**
     * 每周任务、等级1、触发值1
     */
    @Test
    void test_week_task_level_1_trigger_value_1() {
        long taskId = 1L;
        TaskDTO task = buildTaskDTO(taskId, "每周任务_等级1_触发值1", 1, CycleTypeEnum.WEEK, "10000001");
        List<TaskLevelDTO> taskLevelList = buildTaskLevels("每周任务_等级1_触发值1(等级1),1,1");

        all_task_finished(taskId, task, taskLevelList);
        never_task_upload(taskId, task, taskLevelList);
        task_un_finished_trigger_value_invalid(taskId, task, taskLevelList);
    }

    /**
     * 任务(所有等级)已完成
     */
    private void all_task_finished(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                1, TaskStatusEnum.FINISHED, LocalDateTime.now(), LocalDateTime.now());

        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1L, result.getUserTask().getId());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 从未做过任务
     */
    private void never_task_upload(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值过期
     */
    private void task_un_finished_trigger_value_invalid(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                1, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());
    }

    /**
     * 每周任务、等级1、触发值10
     */
    @Test
    void test_week_task_level_1_trigger_value_10() {
        long taskId = 1L;
        TaskDTO task = buildTaskDTO(taskId, "每周任务_等级1_触发值10", 1, CycleTypeEnum.WEEK, "10000002");
        List<TaskLevelDTO> taskLevelList = buildTaskLevels("每周任务_等级1_触发值10(等级1),1,10");

        all_task_finished_2(taskId, task, taskLevelList);
        never_task_upload_2(taskId, task, taskLevelList);
        task_un_finished_trigger_value_invalid_2(taskId, task, taskLevelList);
        task_un_finished_trigger_value_effective_2(taskId, task, taskLevelList);
    }

    /**
     * 任务(所有等级)已完成
     */
    private void all_task_finished_2(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                10, TaskStatusEnum.FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1L, result.getUserTask().getId());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 从未做过任务
     */
    private void never_task_upload_2(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        //任务完成
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务未完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 3);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值过期
     */
    private void task_un_finished_trigger_value_invalid_2(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                10, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        //任务完成
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务未完成
        userTask = buildUserTask(1L, taskId, 1,
                10, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 3);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值未过期
     */
    private void task_un_finished_trigger_value_effective_2(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                8, TaskStatusEnum.UN_FINISHED, now, now);
        //任务完成
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 2);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务未完成
        userTask = buildUserTask(1L, taskId, 1,
                8, TaskStatusEnum.UN_FINISHED, now, now);
        now = LocalDateTime.now();
        userTask = buildUserTask(1L, taskId, 1,
                8, TaskStatusEnum.UN_FINISHED, now, now);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(9, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 每周任务、等级3、触发值1
     */
    @Test
    void test_week_task_level_3_trigger_value_1() {
        long taskId = 1L;
        TaskDTO task = buildTaskDTO(taskId, "每周任务_等级3_触发值1", 3, CycleTypeEnum.WEEK, "10000003");
        List<TaskLevelDTO> taskLevelList = buildTaskLevels("每周任务_等级3_触发值1(等级1),1,1;每周任务_等级3_触发值1(等级2),2,2;每周任务_等级3_触发值1(等级3),3,3");

        all_task_finished_3(taskId, task, taskLevelList);
        never_task_upload_3(taskId, task, taskLevelList);
        task_un_finished_trigger_value_invalid_3(taskId, task, taskLevelList);
        task_un_finished_trigger_value_effective_3(taskId, task, taskLevelList);
    }

    /**
     * 任务(所有等级)已完成
     */
    private void all_task_finished_3(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(1L, taskId, 3,
                3, TaskStatusEnum.FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1L, result.getUserTask().getId());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 从未做过任务
     */
    private void never_task_upload_3(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        //任务等级1完成
        UserTaskDTO userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级1、2完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 2);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(2, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());


        //任务等级1、2、3完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 3);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(3, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值过期
     */
    private void task_un_finished_trigger_value_invalid_3(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        //任务等级1过期  任务等级1完成
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                1, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级2过期  任务等级1完成
        userTask = buildUserTask(1L, taskId, 2,
                2, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级3过期  任务等级1完成
        userTask = buildUserTask(1L, taskId, 3,
                3, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级3过期  任务等级1、2完成
        userTask = buildUserTask(1L, taskId, 3,
                4, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 2);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(2, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());


        //任务等级3过期  任务等级1、2、3完成
        userTask = buildUserTask(1L, taskId, 3,
                4, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 3);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(3, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值未过期
     */
    private void task_un_finished_trigger_value_effective_3(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        //任务完成
        UserTaskDTO userTask = buildUserTask(1L, taskId, 2,
                1, TaskStatusEnum.UN_FINISHED, now, now);
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(2, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        userTask = buildUserTask(1L, taskId, 2,
                1, TaskStatusEnum.UN_FINISHED, now, now);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 2);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());


        userTask = buildUserTask(1L, taskId, 3,
                2, TaskStatusEnum.UN_FINISHED, now, now);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 2);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(4, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());
    }

    /**
     * 每周任务、等级3、触发值10
     */
    @Test
    void test_week_task_level_3_trigger_value_10() {
        long taskId = 1L;
        TaskDTO task = buildTaskDTO(taskId, "每周任务_等级3_触发值10", 3, CycleTypeEnum.WEEK, "10000004");
        List<TaskLevelDTO> taskLevelList = buildTaskLevels("每周任务_等级3_触发值10(等级1),1,10;每周任务_等级3_触发值10(等级2),2,20;每周任务_等级3_触发值10(等级3),3,30");

        all_task_finished_4(taskId, task, taskLevelList);
        never_task_upload_4(taskId, task, taskLevelList);
        task_un_finished_trigger_value_invalid_4(taskId, task, taskLevelList);
        task_un_finished_trigger_value_effective_4(taskId, task, taskLevelList);
    }

    /**
     * 任务(所有等级)已完成
     */
    private void all_task_finished_4(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        UserTaskDTO userTask = buildUserTask(1L, taskId, 3,
                30, TaskStatusEnum.FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 1);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(1L, result.getUserTask().getId());
        assertEquals(30, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());
    }

    /**
     * 从未做过任务
     */
    private void never_task_upload_4(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        //任务未完成
        UserTaskDTO userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 3);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(false, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(3, result.getUserTask().getTriggerValue());
        assertEquals(1, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(0, result.getUserTaskRecords().size());


        //任务等级1完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级1、2完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 20);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(20, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());


        //任务等级1、2、3完成
        userTask = buildUserTask(null, taskId, 1,
                0, TaskStatusEnum.UN_FINISHED, LocalDateTime.now(), LocalDateTime.now());
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 30);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(30, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(3, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值过期
     */
    private void task_un_finished_trigger_value_invalid_4(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusWeeks(-1);
        //任务等级1过期  任务等级1完成
        UserTaskDTO userTask = buildUserTask(1L, taskId, 1,
                10, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级2过期  任务等级1完成
        userTask = buildUserTask(1L, taskId, 2,
                20, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级3过期  任务等级1完成
        userTask = buildUserTask(1L, taskId, 3,
                30, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 10);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(10, result.getUserTask().getTriggerValue());
        assertEquals(2, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        //任务等级3过期  任务等级1、2、3完成
        userTask = buildUserTask(1L, taskId, 3,
                30, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 20);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(20, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());

        //任务等级3过期  任务等级1、2、3完成
        userTask = buildUserTask(1L, taskId, 3,
                30, TaskStatusEnum.FINISHED, localDateTime, localDateTime);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 30);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(30, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(3, result.getUserTaskRecords().size());
    }

    /**
     * 旧触发值未过期
     */
    private void task_un_finished_trigger_value_effective_4(long taskId, TaskDTO task, List<TaskLevelDTO> taskLevelList) {
        LocalDateTime now = LocalDateTime.now();
        //任务完成
        UserTaskDTO userTask = buildUserTask(1L, taskId, 2,
                12, TaskStatusEnum.UN_FINISHED, now, now);
        TaskCalculationHandle taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 8);
        taskCalculationHandle.handle();
        TaskCalculationHandle.TaskCalculationHandleResult result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(20, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.UN_FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());


        userTask = buildUserTask(1L, taskId, 2,
                13, TaskStatusEnum.UN_FINISHED, now, now);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 17);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(30, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(2, result.getUserTaskRecords().size());


        userTask = buildUserTask(1L, taskId, 3,
                23, TaskStatusEnum.UN_FINISHED, now, now);
        taskCalculationHandle = new TaskCalculationHandle(task, taskLevelList, userTask, 12);
        taskCalculationHandle.handle();
        result = taskCalculationHandle.getResult();
        assertNotNull(result);
        assertEquals(true, result.getFinished());

        assertNotNull(result.getUserTask());
        assertEquals(35, result.getUserTask().getTriggerValue());
        assertEquals(3, result.getUserTask().getCurrentLevel());
        assertEquals(TaskStatusEnum.FINISHED, result.getUserTask().getTaskStatus());

        assertNotNull(result.getUserTaskRecords());
        assertEquals(1, result.getUserTaskRecords().size());
    }

}