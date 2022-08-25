package com.yw.task.common.enums;

import com.yyw.api.exception.BusinessException;

import java.util.Arrays;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 15:08
 */
public enum TaskRewardTypeEnum {
    /**
     *
     */
    SCORE(1, "积分"),
    GOLD_COIN(2, "金币");

    private final Integer type;
    private final String name;

    TaskRewardTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static TaskRewardTypeEnum findByType(Integer type) {
        return Arrays.stream(TaskRewardTypeEnum.values())
                .filter(taskRewardTypeEnum -> taskRewardTypeEnum.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new BusinessException(TaskResponseCode.TASK_REWARD_TYPE_ERROR));
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
