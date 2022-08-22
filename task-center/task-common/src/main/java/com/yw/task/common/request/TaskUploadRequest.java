package com.yw.task.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务事件上报参数
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:59
 */
@Data
public class TaskUploadRequest {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 事件标识id
     */
    @NotBlank(message = "事件标识ID不能为空")
    private String eventId;

    /**
     * 数值
     */
    private Integer triggerValue = 1;


}
