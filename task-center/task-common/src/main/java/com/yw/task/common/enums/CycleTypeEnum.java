package com.yw.task.common.enums;

import com.yyw.api.exception.BusinessException;

import java.util.Arrays;

/**
 * 任务周期类型
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 17:19
 */
public enum CycleTypeEnum {
    /**
     *
     */
    PERMANENT(1, "永久，完成即止"),
    DAY(2, "任务周期为每日"),
    WEEK(3, "任务周期为每周");

    private final Integer code;

    private final String name;

    public static CycleTypeEnum findByCode(Integer code) {
        return Arrays.stream(CycleTypeEnum.values())
                .filter(cycleTypeEnum -> cycleTypeEnum.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new BusinessException(TaskResponseCode.INCORRECT_CYCLE_TYPE));
    }

    CycleTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
