package com.example.config;

import com.example.serviceribbon.rule.MyRandomRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanzt
 * @date 2018/8/28 9:58
 * @describe
 */
@Configuration
public class RibbonConfig {

    /**
     * 配置自定义负载均衡策略
     * */
//    @Bean
    public IRule myRule(){
        return new MyRandomRule();
    }

    @Bean
    public IRule randomRule(){
        return new RandomRule();
    }
}
