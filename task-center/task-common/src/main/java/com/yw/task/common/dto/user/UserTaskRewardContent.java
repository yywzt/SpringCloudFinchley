package com.yw.task.common.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 任务奖励数据
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 15:08
 */
@Data
@AllArgsConstructor
public class UserTaskRewardContent {

    /**
     * 任务奖励类型
     * {@link com.yw.task.common.enums.TaskRewardTypeEnum}
     */
    private Integer type;

    private Integer value;

}
