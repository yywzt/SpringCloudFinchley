package com.example.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanzt
 * @date 2018/8/15 13:37
 * @describe
 */
@RestController
public class IndexController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public Map<String, Object> home(@RequestParam(value = "name", defaultValue = "forezp",required = false) String name) {
        String s = "hi " + name + " ,i am from port:" + port;
        System.out.println(s);
        try {
            Thread.sleep(Long.valueOf(name));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("return");
        int code = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",s);
        return map;
    }

}
