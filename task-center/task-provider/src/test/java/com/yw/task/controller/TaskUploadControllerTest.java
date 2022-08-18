package com.yw.task.controller;

import com.yw.task.common.request.TaskUploadRequest;
import com.yw.task.config.JacksonConfig;
import com.yw.task.service.TaskUploadService;
import com.yw.task.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 14:59
 */
@AutoConfigureMybatis
@ExtendWith(SpringExtension.class)
@WebMvcTest({TaskUploadController.class, JacksonConfig.class, JsonUtil.class})
class TaskUploadControllerTest {

    protected static final String SUCCESS_RESULT = "{\"code\":\"0\",\"message\":\"OK\"}";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private JsonUtil jsonUtil;

    @MockBean
    private TaskUploadService taskUploadService;

    @Test
    void test_upload_param_empty () throws Exception {
        TaskUploadRequest taskUploadRequest = new TaskUploadRequest();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/task/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(jsonUtil.toJson(taskUploadRequest));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\":\"task-10001\",\"message\":\"参数校验未通过: [事件标识ID不能为空, 用户ID不能为空]\"}", true));
    }

    @Test
    void test_upload_success () throws Exception {
        TaskUploadRequest taskUploadRequest = new TaskUploadRequest();
        taskUploadRequest.setUserId(1L);
        taskUploadRequest.setEventId("11111111");
        taskUploadRequest.setTriggerValue(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/task/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(jsonUtil.toJson(taskUploadRequest));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(SUCCESS_RESULT, true));
    }
}