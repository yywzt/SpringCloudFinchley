package com.yw.ywregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yanzhitao@xiaomalixing.com
 */
@EnableEurekaServer
@SpringBootApplication
public class YwRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(YwRegisterApplication.class, args);
    }

}
