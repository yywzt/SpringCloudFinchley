package com.yw.task.config;

import com.yyw.framework.util.IdWorkerUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法生成器配置
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/30 16:21
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "idWorker")
public class IdGenderConfig {

    private long workerId;

    private Long dataCenterId;

    @Bean
    public IdWorkerUtil idWorkerUtil() {
        return new IdWorkerUtil(workerId, dataCenterId);
    }
}
