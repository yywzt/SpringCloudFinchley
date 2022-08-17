package com.yw.user.controller;

import com.yyw.api.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/5/14 17:16
 */
@Slf4j
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class DefaultErrorController extends AbstractErrorController {

    @Autowired
    public DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    @RequestMapping
    public ResponseEntity<ResponseData<Object>> doHandleError(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);
        log.error("status: {}, body: {}", status, body);
        ResponseData<Object> result = ResponseData.failure(status.value() + "", body.get("error") + "," + body.get("message"));
        return new ResponseEntity<>(result, status);
    }

}
