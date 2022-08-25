package com.yw.task.common.enums;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 15:14
 */
public enum GrantStatusEnum {
    /**
     * 待发放
     */
    AWAIT_GRANT(0, "待发放"),
    GRANT_FINISHED(1, "发放完成"),
    GRANT_FAILURE(2, "发放失败");

    private final Integer status;

    private final String name;

    GrantStatusEnum(Integer status, String name) {
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
