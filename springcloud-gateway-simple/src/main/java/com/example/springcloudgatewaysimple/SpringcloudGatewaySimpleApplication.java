package com.example.springcloudgatewaysimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringcloudGatewaySimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGatewaySimpleApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        /**
         * 同
         * - id: neo_route
         *           uri: http://www.ityouknow.com
         *           predicates:
         *             - Path=/spring-cloud
         * */
//        return builder.routes()
//                .route("neo_route", predicateSpec -> predicateSpec.path("/spring-cloud").uri("http://www.ityouknow.com"))
//                .build();
        String httpUri = "http://127.0.0.1:18797/testfallback";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route("hystrix", p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }
}
