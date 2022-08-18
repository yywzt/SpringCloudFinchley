package com.yw.task.common.model.user;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 用户任务进度
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:31
 */
@Data
@Table(name = "user_task")
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private Long userId;

    /**
     * 用户当前任务等级
     */
    private Integer level;

    /**
     * 当前触发值
     */
    private Integer triggerValue;

    /**
     * 用户当前任务等级状态
     * <p>
     * {@link com.yw.task.common.enums.TaskStatusEnum}
     */
    private Integer status;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
