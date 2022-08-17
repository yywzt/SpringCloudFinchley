package com.yw.task.controller;

import com.yw.task.common.request.TaskUploadRequest;
import com.yw.task.service.TaskUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 任务事件上报
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:58
 */
@RestController
@RequestMapping("/task")
public class TaskUploadController {

    @Resource
    private TaskUploadService taskUploadService;

    @PostMapping("/upload")
    public void upload(@Valid @RequestBody TaskUploadRequest taskUploadRequest) {
        taskUploadService.upload(taskUploadRequest);
    }
}
