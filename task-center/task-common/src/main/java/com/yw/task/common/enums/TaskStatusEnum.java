package com.yw.task.common.enums;

import java.util.Arrays;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:40
 */
public enum TaskStatusEnum {

    /**
     * 未完成
     */
    UN_FINISHED(0, "未完成"),
    /**
     * 已完成
     */
    FINISHED(1, "已完成"),
    /**
     * 未开始
     */
    NO_STARTED(2, "未开始");

    private final Integer status;
    private final String name;

    public static TaskStatusEnum findByStatus(Integer status) {
        return Arrays.stream(TaskStatusEnum.values())
                .filter(taskStatusEnum -> taskStatusEnum.getStatus().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("任务状态标识异常"));
    }

    TaskStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
