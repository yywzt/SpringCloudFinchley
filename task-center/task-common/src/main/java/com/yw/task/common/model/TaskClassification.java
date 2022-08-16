package com.yw.task.common.model;

import com.yyw.api.enums.EnableStatusEnum;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 任务分类
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 17:22
 */
@Data
@Table(name = "task_classification")
public class TaskClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务分类名称
     */
    private String name;

    /**
     * 排序
     * 小->优先级高
     */
    private Integer sort;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    /**
     * {@link EnableStatusEnum}
     */
    private Integer enableStatus;
}
