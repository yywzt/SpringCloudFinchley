package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @EnableConfigServer : 开启配置服务器的功能
 *
 * 远程仓库https://github.com/yywzt/SpringcloudConfig/ 中有个文件zuul-dev.properties文件中有一个属性：
 * 		democonfigclient.message=yyww
 * 		启动程序：访问http://localhost:8888/zuul/dev
 * 		{"name":"zuul","profiles":["dev"],"label":null,"version":"a7faaf5f9bf94519d0c7fc713d85693456624a68","state":null,
 * 			"propertySources":[{"name":"https://github.com/yywzt/SpringcloudConfig.git/config/zuul-dev.properties",
 * 				"source":{"democonfigclient.message":"yyww"}}]}
 *		证明配置服务中心可以从远程程序获取配置信息。
 *
 * http请求地址和资源文件映射如下:
 * 		/{application}/{profile}[/{label}]
 * 		/{application}-{profile}.yml
 * 		/{label}/{application}-{profile}.yml
 * 		/{application}-{profile}.properties
 * 		/{label}/{application}-{profile}.properties
 * */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
