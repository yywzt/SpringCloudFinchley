package com.example.servicefeign.interFace;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yanzt
 * @date 2018/8/16 9:54
 * @describe
 *  通过@ FeignClient（“服务名”），来指定调用哪个服务
 */
@FeignClient(value = "service-test",fallback = ServiceHiClientFallback.class )
//@FeignClient(value = "service-test",fallbackFactory = ServiceHiClientFallbackFactory.class )
public interface ServiceHiClient {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}

/**
 * fallbackFactory方式
 */
@Component
class ServiceHiClientFallbackFactory implements FallbackFactory<ServiceHiClient> {

    @Override
    public ServiceHiClient create(Throwable cause) {
        return new ServiceHiClient() {
            @Override
            public String sayHiFromClientOne(String name) {
                return new StringBuffer("hi,").append(name).append(",sorry,error!").toString();
            }
        };
    }
}

/**
 * fallback方式
 * */
@Component
class ServiceHiClientFallback implements ServiceHiClient{

    @Override
    public String sayHiFromClientOne(String name) {
        return new StringBuffer("hi,").append(name).append(",sorry,error!").toString();
    }
}
