package com.yw.task.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 17:35
 */
@Data
public class TaskDetailsRequest {

    @NotNull(message = "任务ID不能为空")
    private Long taskId;

    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
