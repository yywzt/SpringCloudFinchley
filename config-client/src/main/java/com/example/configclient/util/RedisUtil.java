package com.example.configclient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/8/14 15:34
 * @describe
 */
@Configuration
public class RedisUtil {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }
}