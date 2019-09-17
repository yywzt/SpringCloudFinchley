package com.example.springcloudgatewaysimple.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/8/27 21:02
 */
@RestController
public class FallBackController {

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    @RequestMapping("/testfallback")
    public Mono<String> testfallback() {
        return Mono.just("testfallback");
    }
}
