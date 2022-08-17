package com.yw.user.controller;

import com.yw.user.common.model.User;
import com.yw.user.config.JacksonConfig;
import com.yw.user.service.UserService;
import com.yyw.api.enums.EnableStatusEnum;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 10:35
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest({UserController.class, JacksonConfig.class})
class UserControllerTest {

    protected static final String SUCCESS_RESULT = "{\"code\":\"0\",\"message\":\"OK\"}";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void test_user() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        Mockito.when(userService.list(any())).thenReturn(null);
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(SUCCESS_RESULT));


        requestBuilder = MockMvcRequestBuilders.get("/user/list")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page", "0")
                .param("size", "10");
        Mockito.when(userService.list(any())).thenReturn(null);
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(SUCCESS_RESULT));


        requestBuilder = MockMvcRequestBuilders.get("/user/list")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page", "0")
                .param("size", "10");
        Mockito.when(userService.list(any())).thenReturn(Lists.newArrayList(mockUser(), mockUser()));
        String expectedContent = "{\"code\":\"0\",\"message\":\"OK\",\"data\":[{\"id\":1,\"name\":\"a\",\"password\":\"aaa\",\"createDate\":\"2022-08-17 08:00:00.000\",\"modifyDate\":\"2022-08-17 08:00:00.000\",\"enableStatus\":1},{\"id\":1,\"name\":\"a\",\"password\":\"aaa\",\"createDate\":\"2022-08-17 08:00:00.000\",\"modifyDate\":\"2022-08-17 08:00:00.000\",\"enableStatus\":1}]}";
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent))
                .andExpect(MockMvcResultMatchers.content().json(expectedContent));
    }

    private User mockUser() {
        User user = new User();
        user.setId(1L);
        user.setName("a");
        user.setPassword("aaa");
        user.setCreateDate(LocalDateTime.of(2022, 8, 17, 8, 0, 0, 0));
        user.setModifyDate(LocalDateTime.of(2022, 8, 17, 8, 0, 0, 0));
        user.setEnableStatus(EnableStatusEnum.ENABLE_STATUS.getCode());
        return user;
    }
}