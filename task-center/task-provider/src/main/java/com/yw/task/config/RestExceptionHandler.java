package com.yw.task.config;

import com.google.common.collect.Lists;
import com.yw.task.common.enums.TaskResponseCode;
import com.yyw.api.model.ResponseCode;
import com.yyw.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;

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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseData<Object> exception(Exception e) {
        log.error("全局异常信息", e);
        return ResponseData.failure(ResponseCode.UNKNOWN_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseData<Object> illegalArgumentExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("IllegalArgumentExceptionHandler return!!!", e);
        return ResponseData.failure(ResponseCode.SELECT_PAGINATION_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IOException.class)
    public ResponseData<Object> ioExceptionErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("ioExceptionErrorHandler done!!!", e);
        return ResponseData.failure(ResponseCode.UNKNOWN_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseData<Object> missingServletRequestParameterErrorHandler(HttpServletRequest request, Exception e) {
        log.error("MissingServletRequestParameterErrorHandler: {}", e.getMessage());
        return ResponseData.failure(TaskResponseCode.PARAM_IS_NULL);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseData<Object> httpRequestMethodNotSupportedErrorHandler(HttpServletRequest request, Exception e) {
        log.error("httpRequestMethodNotSupportedErrorHandler: {}", e.getMessage());
        return ResponseData.failure(ResponseCode.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
    }

    /**
     * 参数校验未通过
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseData<Object> handleBindException(BindException e, HttpServletRequest request) {
        log.error("handleBindException return", e);
        List<String> errorMessages = buildBindingResultMsg(e.getBindingResult());
        String message = MessageFormat.format(TaskResponseCode.PARAM_CHECK_FAILURE.getMessage(), errorMessages);
        return ResponseData.failure(TaskResponseCode.PARAM_CHECK_FAILURE.getCode(), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                       HttpServletRequest request) {
        log.error("handlerMethodArgumentNotValidException return", e);
        List<String> errorMessages = buildBindingResultMsg(e.getBindingResult());
        String message = MessageFormat.format(TaskResponseCode.PARAM_CHECK_FAILURE.getMessage(), errorMessages);
        return ResponseData.failure(TaskResponseCode.PARAM_CHECK_FAILURE.getCode(), message);
    }

    private List<String> buildBindingResultMsg(BindingResult bindingResult) {
        List<String> msg = Lists.newArrayList();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.stream()
                    .sorted(Comparator.comparing(FieldError::getField))
                    .forEach(fieldError -> msg.add(fieldError.getDefaultMessage()));
        }
        return msg;
    }
}
