package com.yw.user.common.enums;

import com.yyw.api.exception.BusinessException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:37
 */
public enum AssetsChangeTypeEnum {
    /**
     *
     */
    IN(0, "收入"),
    OUT(1, "支出");

    private final int type;
    private final String name;

    public static AssetsChangeTypeEnum findByType(Integer type) {
        return Arrays.stream(AssetsChangeTypeEnum.values())
                .filter(assetsChangeTypeEnum -> Objects.equals(type, assetsChangeTypeEnum.getType()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(UserResponseCode.ASSETS_CHANGE_TYPE_ERROR));
    }

    AssetsChangeTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
