package com.yw.task.service;

import com.yw.task.common.dto.TaskDTO;
import com.yw.task.common.enums.CycleTypeEnum;
import com.yw.task.common.model.Task;
import com.yw.task.mapper.TaskMapper;
import com.yw.task.service.user.UserTaskService;
import com.yyw.api.enums.EnableStatusEnum;
import com.yyw.api.exception.BusinessException;
import com.yyw.api.vo.PageInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:21
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;
    @Mock
    private UserTaskService userTaskService;
    @Mock
    private TaskLevelService taskLevelService;
    @InjectMocks
    private TaskService taskService;

    @Test
    void test_get_throw_exception() {
        Long id = 1L;
        Mockito.when(taskMapper.get(id, EnableStatusEnum.ENABLE_STATUS.getCode()))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> {
            taskService.get(id);
        });
        assertEquals("任务不存在", exception.getMessage());
    }

    @Test
    void test_get_result() {
        Long id = 1L;
        Task task = mockTask();
        Mockito.when(taskMapper.get(id, EnableStatusEnum.ENABLE_STATUS.getCode()))
                .thenReturn(Optional.of(task));

        TaskDTO taskDTO = assertDoesNotThrow(() -> taskService.get(id));
        assertNotNull(taskDTO);
        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getLevel(), taskDTO.getLevel());
        assertEquals(task.getCycleType(), taskDTO.getCycleType());
        assertEquals(task.getEventId(), taskDTO.getEventId());
    }

    private Task mockTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("任务1");
        task.setDescription("任务1-描述");
        task.setClassificationId(1L);
        task.setClassificationName("每周任务");
        task.setLevel(1);
        task.setCycleType(CycleTypeEnum.WEEK.getCode());
        task.setEventId("10000001");
        task.setCreateDate(LocalDateTime.now());
        task.setModifyDate(LocalDateTime.now());
        task.setEnableStatus(EnableStatusEnum.ENABLE_STATUS.getCode());
        return task;
    }

    @Test
    void page_empty() {
        long classificationId = 1L;
        int page = 1;
        int size = 10;
        Mockito.when(taskMapper.list(classificationId, EnableStatusEnum.ENABLE_STATUS.getCode()))
                .thenReturn(Collections.emptyList());
        PageInfoVO<Task> taskPageInfo = taskService.page(classificationId, page, size);

        assertNotNull(taskPageInfo);
        assertEquals(size, taskPageInfo.getPageSize());
        assertEquals(0, taskPageInfo.getTotals());
        assertEquals(0, taskPageInfo.getTotalPages());
        assertEquals(page, taskPageInfo.getCurrentPage());
        assertNotNull(taskPageInfo.getList());
    }

    @Test
    void list() {
//        TaskListRequest taskListRequest = new TaskListRequest();
//        System.out.println(taskService.list(taskListRequest));
    }

    @Test
    void details() {
    }
}