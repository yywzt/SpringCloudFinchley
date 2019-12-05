package com.example.yywauth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yw
 * @create 2018-06-20 16:17
 * @description: 扩展MVC功能
 */
//添加该注解  则全面接管springMVC  所有的SpringMVC的自动配置都失效
//@EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域处理
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:19091", "null", "*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("Content-Type", "X-Token")
                .maxAge(3600L);
    }

}
