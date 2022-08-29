package com.yw.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yanzhitao@xiaomalixing.com
 */
@EnableDiscoveryClient
@SpringBootApplication
public class YwGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(YwGatewayApplication.class, args);
	}

}
