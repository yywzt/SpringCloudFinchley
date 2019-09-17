package com.example.apolloconfig;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig
public class ApolloConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApolloConfigApplication.class, args);
    }

}
