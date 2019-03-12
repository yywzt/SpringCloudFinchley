package com.example.serviceribbon;

import com.example.config.RibbonConfig;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *  服务与服务的通讯是基于http restful的。
 *  Spring cloud有两种服务调用方式： 一种是ribbon+restTemplate，另一种是feign
 *  服务消费者（rest+ribbon）
 *
 *  @EnableHystrix : 注解开启Hystrix
 *  @EnableHystrixDashboard : 开启Hystrix Dashboard仪表盘
 * */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@RibbonClient(name = "SERVICE-TEST", configuration = RibbonConfig.class)
public class ServiceRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}

	/**
	 * @LoadBalanced 注解表示restTemplate开启负载均衡的功能
	 * */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	/**
	 * Hystrix Dashboard仪表盘出现 Unable to connect to Command Metric Stream.异常
	 *
	 * 在2.0版本下需要在启动类中添加如下配置才可以访问断路器仪表盘
	 * 访问url为：http://localhost:port/hystrix
	 * 在页面填写路径： http://localhost:port/hystrix.stream
	 * */
	@Bean
	public ServletRegistrationBean getServlet(){
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/actuator/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}

}
