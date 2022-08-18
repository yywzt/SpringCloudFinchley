package com.yw.task.controller;

import com.google.common.collect.Lists;
import com.yw.task.common.enums.TaskStatusEnum;
import com.yw.task.common.response.TaskDetailsResponse;
import com.yw.task.common.response.TaskLevelResponse;
import com.yw.task.common.response.TaskListResponse;
import com.yw.task.config.JacksonConfig;
import com.yw.task.service.TaskService;
import com.yyw.api.vo.PageInfoVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 16:33
 */
@AutoConfigureMybatis
@ExtendWith(SpringExtension.class)
@WebMvcTest({TaskController.class, JacksonConfig.class})
class TaskControllerTest {

    protected static final String SUCCESS_RESULT = "{\"code\":\"0\",\"message\":\"OK\"}";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void test_list_param_empty() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/list");
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\":\"task-10001\",\"message\":\"参数校验未通过: [任务分类ID不能为空, 用户ID不能为空]\"}", true));
    }

    @Test
    void test_list_result_not_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/list")
                .param("page", "1")
                .param("size", "10")
                .param("userId", "1")
                .param("classificationId", "1");
        Mockito.when(taskService.list(any())).thenReturn(null);
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(SUCCESS_RESULT, true));
    }

    @Test
    void test_list_result_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/list")
                .param("page", "1")
                .param("size", "10")
                .param("userId", "1")
                .param("classificationId", "1");

        String jsonContent = "{\"code\":\"0\",\"message\":\"OK\",\"data\":{\"pageSize\":10,\"totals\":2,\"totalPages\":1,\"currentPage\":1,\"list\":[{\"id\":1,\"title\":\"任务1\",\"level\":1,\"currentLevel\":1,\"taskStatus\":1,\"taskLevel\":[{\"title\":\"任务等级1\",\"level\":1,\"taskStatus\":1}]},{\"id\":2,\"title\":\"任务2\",\"level\":1,\"currentLevel\":1,\"taskStatus\":0,\"taskLevel\":[{\"title\":\"任务等级1\",\"level\":1,\"taskStatus\":0}]}]}}";
        Mockito.when(taskService.list(any())).thenReturn(mockTaskListResponsePageInfo());
        this.mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonContent, true));
    }

    private PageInfoVO<TaskListResponse> mockTaskListResponsePageInfo() {
        return new PageInfoVO<>(10, 2, 1, 1
                , Lists.newArrayList(mockTaskListResponse1(), mockTaskListResponse2()));
    }

    private TaskListResponse mockTaskListResponse2() {
        TaskListResponse taskListResponse2 = new TaskListResponse();
        taskListResponse2.setId(2L);
        taskListResponse2.setTitle("任务2");
        taskListResponse2.setLevel(1);
        taskListResponse2.setCurrentLevel(1);
        taskListResponse2.setTaskStatus(TaskStatusEnum.UN_FINISHED.getStatus());

        TaskLevelResponse taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setTitle("任务等级1");
        taskLevelResponse.setLevel(1);
        taskLevelResponse.setTaskStatus(TaskStatusEnum.UN_FINISHED.getStatus());
        taskListResponse2.setTaskLevel(Lists.newArrayList(taskLevelResponse));
        return taskListResponse2;
    }

    private TaskListResponse mockTaskListResponse1() {
        TaskListResponse taskListResponse1 = new TaskListResponse();
        taskListResponse1.setId(1L);
        taskListResponse1.setTitle("任务1");
        taskListResponse1.setLevel(1);
        taskListResponse1.setCurrentLevel(1);
        taskListResponse1.setTaskStatus(TaskStatusEnum.FINISHED.getStatus());

        TaskLevelResponse taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setTitle("任务等级1");
        taskLevelResponse.setLevel(1);
        taskLevelResponse.setTaskStatus(TaskStatusEnum.FINISHED.getStatus());
        taskListResponse1.setTaskLevel(Lists.newArrayList(taskLevelResponse));
        return taskListResponse1;
    }

    @Test
    void test_details_param_null() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/details");

        this.mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_details_result_not_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/details/1");

        Mockito.when(taskService.details(any())).thenReturn(null);
        this.mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(SUCCESS_RESULT, true));
    }

    @Test
    void test_details_result_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/details/1");

        String jsonContent = "{\"code\":\"0\",\"message\":\"OK\",\"data\":{\"id\":2,\"title\":\"任务2\",\"level\":1,\"currentLevel\":1,\"taskStatus\":0,\"taskLevel\":[{\"title\":\"任务等级1\",\"level\":1,\"taskStatus\":0}]}}";
        Mockito.when(taskService.details(any())).thenReturn(mockTaskDetailsResponse());
        this.mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonContent, true));
    }

    private TaskDetailsResponse mockTaskDetailsResponse() {
        TaskDetailsResponse taskDetailsResponse = new TaskDetailsResponse();
        taskDetailsResponse.setId(2L);
        taskDetailsResponse.setTitle("任务2");
        taskDetailsResponse.setLevel(1);
        taskDetailsResponse.setCurrentLevel(1);
        taskDetailsResponse.setTaskStatus(TaskStatusEnum.UN_FINISHED.getStatus());

        TaskLevelResponse taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setTitle("任务等级1");
        taskLevelResponse.setLevel(1);
        taskLevelResponse.setTaskStatus(TaskStatusEnum.UN_FINISHED.getStatus());
        taskDetailsResponse.setTaskLevel(Lists.newArrayList(taskLevelResponse));
        return taskDetailsResponse;
    }
}