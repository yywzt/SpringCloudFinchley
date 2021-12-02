package com.yyw.social.controller;

import com.yyw.api.model.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/11/30 12:02
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public ResponseData<Object> index() {
        return ResponseData.success();
    }
}
