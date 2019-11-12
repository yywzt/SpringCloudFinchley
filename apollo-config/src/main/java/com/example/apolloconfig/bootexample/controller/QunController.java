package com.example.apolloconfig.bootexample.controller;

import com.example.apolloconfig.bootexample.service.QunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/10/11 16:21
 */
@RestController
@RequestMapping("/qun")
public class QunController {

    @Autowired
    private QunService qunService;

    @RequestMapping("/list")
    public Object list(){
        return qunService.listQun();
    }

    @RequestMapping("/pageQun")
    public Object pageQun(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "20") int size){
        return qunService.pageQun(page, size);
    }

}
