package com.yw.task.common.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 任务等级奖励
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:58
 */
@Data
@Table(name = "task_level_reward")
public class TaskLevelReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    /**
     * 任务等级
     */
    private Integer level;

    /**
     * 奖励类型
     * {@link com.yw.task.common.enums.TaskRewardTypeEnum}
     */
    private Integer type;

    /**
     * 奖励数值
     */
    private Integer value;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
