package com.yw.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author yanzhitao@xiaomalixing.com
 */
@MapperScan(value = {"com.yw.user.mapper"})
@EnableDiscoveryClient
@SpringBootApplication
public class UserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }

}
