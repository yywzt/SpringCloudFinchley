package com.yw.task.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:03
 */
@Slf4j
@Component
public class JsonUtil {

    @Resource
    private ObjectMapper objectMapper;

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 解析数组json字符串成对象List集合
     */
    public <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     */
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("write to json string error:" + object, e);
            return null;
        }
    }
}
