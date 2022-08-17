package com.yw.task.controller;

import com.yw.task.common.request.TaskListRequest;
import com.yw.task.common.response.TaskDetailsResponse;
import com.yw.task.common.response.TaskListResponse;
import com.yw.task.service.TaskService;
import com.yyw.api.vo.PageInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:00
 */
@RestController
@RequestMapping("/task/")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 任务列表
     *
     * @param taskListRequest 参数
     */
    @GetMapping("/list")
    public PageInfoVO<TaskListResponse> list(@Valid TaskListRequest taskListRequest) {
        return taskService.list(taskListRequest);
    }

    /**
     * 任务详情
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    @GetMapping("/details/{taskId}")
    public TaskDetailsResponse details(@PathVariable Long taskId) {
        return taskService.details(taskId);
    }
}
