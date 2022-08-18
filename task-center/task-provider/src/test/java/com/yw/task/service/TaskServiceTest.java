package com.yw.task.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:21
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Test
    void list() {
//        TaskListRequest taskListRequest = new TaskListRequest();
//        System.out.println(taskService.list(taskListRequest));
    }

    @Test
    void details() {
    }
}