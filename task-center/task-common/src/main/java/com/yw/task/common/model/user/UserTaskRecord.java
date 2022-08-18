package com.yw.task.common.model.user;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 用户任务记录(已完成)
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:31
 */
@Data
@Table(name = "user_task_record")
public class UserTaskRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private Long userId;

    /**
     * 用户任务等级
     */
    private Integer level;

    /**
     * 任务完成时间
     */
    private LocalDateTime finishedDate;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}
