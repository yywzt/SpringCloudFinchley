package com.yw.task.config;

import com.yyw.api.model.ResponseCode;
import com.yyw.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 14:39
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 默认全局异常处理。
     *
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Object> exception(Exception e) {
        log.error("全局异常信息", e);
        return ResponseData.failure(ResponseCode.UNKNOWN_EXCEPTION);
    }

}
