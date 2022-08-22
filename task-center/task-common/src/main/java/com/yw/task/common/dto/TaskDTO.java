package com.yw.task.common.dto;

import com.yw.task.common.enums.CycleTypeEnum;
import lombok.Data;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:26
 */
@Data
public class TaskDTO {

    private Long id;

    /**
     * 任务名称
     */
    private String title;

    /**
     * 任务总等级
     */
    private Integer level;

    /**
     * 周期类型
     */
    private CycleTypeEnum cycleType;

    /**
     * 事件标识id
     */
    private String eventId;
}
