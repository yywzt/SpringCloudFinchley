package com.example.serviceturbine;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

/**
 * @EnableTurbine : 开启turbine
 * */
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableHystrix
public class ServiceTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTurbineApplication.class, args);
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
