package com.example.serviceribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

/**
 * @author yanzt
 * @date 2018/8/28 10:03
 * @describe 继承com.netflix.loadbalancer.AbstractLoadBalancerRule，
 *           重写choos方法实现自定义负载均衡策略
 */
public class MyRandomRule extends AbstractLoadBalancerRule{
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    /**
     * 自定义负载均衡策略
     * */
    @Override
    public Server choose(Object key) {
        return new RoundRobinRule().choose(key);
    }
}
