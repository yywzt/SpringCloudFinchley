package com.yw.task.service;

import com.yw.task.common.request.TaskListRequest;
import com.yw.task.common.response.TaskDetailsResponse;
import com.yw.task.common.response.TaskListResponse;
import com.yyw.api.vo.PageInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:12
 */
@Service
public class TaskService {

    public PageInfoVO<TaskListResponse> list(TaskListRequest taskListRequest) {
        return null;
    }

    public TaskDetailsResponse details(Long taskId) {
        return null;
    }
}
