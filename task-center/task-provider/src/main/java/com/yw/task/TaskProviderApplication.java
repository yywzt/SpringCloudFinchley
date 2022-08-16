package com.yw.task;

import com.yw.user.common.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {Constant.USER_PACKAGE_NAME})
@SpringBootApplication
public class TaskProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskProviderApplication.class, args);
    }

}
