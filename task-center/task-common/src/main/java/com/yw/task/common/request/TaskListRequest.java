package com.yw.task.common.request;

import com.yyw.api.dto.PageInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 任务列表请求参数
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskListRequest extends PageInfoDTO {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 任务分类ID
     */
    @NotNull(message = "任务分类ID不能为空")
    private Long classificationId;
}

