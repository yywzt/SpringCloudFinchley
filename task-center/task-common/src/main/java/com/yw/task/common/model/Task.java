package com.yw.task.common.model;

import com.yw.task.common.enums.CycleTypeEnum;
import com.yyw.api.enums.EnableStatusEnum;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 16:24
 */
@Data
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务名称
     */
    private String title;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务分类ID
     */
    private Long classificationId;

    /**
     * 任务分类名称
     */
    private String classificationName;

    /**
     * 任务总等级
     */
    private Integer level;

    /**
     * 周期类型
     * {@link CycleTypeEnum}
     */
    private Integer cycleType;

    /**
     * 事件标识id
     */
    private String eventId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    /**
     * {@link EnableStatusEnum}
     */
    private Integer enableStatus;
}
