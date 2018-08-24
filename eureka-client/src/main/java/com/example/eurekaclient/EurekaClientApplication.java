package com.example.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yw
 * @date 2018-08-16
 * @EnableEurekaClient 表明启用一个eurekaclient
 *
 * * @EnableDiscoveryClient 与 @EnableEurekaClient
 * * spring cloud中discovery service有许多种实现（eureka、consul、zookeeper等等），
 * * @EnableDiscoveryClient基于spring-cloud-commons, @EnableEurekaClient基于spring-cloud-netflix。
 * * 其实用更简单的话来说，就是如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。
 *
 *
 * By having spring-cloud-starter-netflix-eureka-client on the classpath, your application automatically registers with the Eureka Server
 * 通过在类路径上拥有Spring Client Netflix eUrKaClient，您的应用程序将自动注册到eURKA服务器。
 * 所以不添加@EnableEurekaClient注解，也可以正常使用
 * */
@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
public class EurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}

}
