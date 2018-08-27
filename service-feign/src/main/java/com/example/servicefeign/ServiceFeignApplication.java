package com.example.servicefeign;

import brave.sampler.Sampler;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 *  服务与服务的通讯是基于http restful的。
 *  Spring cloud有两种服务调用方式： 一种是ribbon+restTemplate，另一种是feign
 *  服务消费者（Feign）
 * @EnableFeignClients 注解开启Feign的功能
 * @EnableHystrixDashboard 开启Hystrix Dashboard仪表盘
 *
 * Feign
 * 	Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。
 * 	它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。
 * 	Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
 * 	简而言之：
 * 		Feign 采用的是基于接口的注解
 * 		Feign 整合了ribbon，具有负载均衡的能力
 * 		Feign 整合了Hystrix，具有熔断的能力
 *
 * */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard
public class ServiceFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFeignApplication.class, args);
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

	@Bean
	public Sampler sampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
}
