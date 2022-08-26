package com.yw.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 11:01
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public Executor grantTaskRewardExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(30);
        //等待任务在关机时完成--true表明等待所有线程执行完成再优雅停机
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时间 （默认为0，此时立即停止），并等待xx秒后强制停止
        executor.setAwaitTerminationSeconds(30);
        executor.setThreadNamePrefix("grant-task-reward-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("Class: {}, Method: {}, Exception: {}", method.getDeclaringClass().getName(), method.getName(), ex);
    }

}
