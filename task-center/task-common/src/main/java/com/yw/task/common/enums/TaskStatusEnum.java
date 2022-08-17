package com.yw.task.common.enums;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 15:40
 */
public enum TaskStatusEnum {

    /**
     * 未完成
     */
    UN_FINISHED(0),
    /**
     * 已完成
     */
    FINISHED(1),
    /**
     * 未开始
     */
    NO_STARTED(2);

    private final Integer status;

    TaskStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
