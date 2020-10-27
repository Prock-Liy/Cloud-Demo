package pers.liy.server.test.service.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.liy.server.test.service.IHelloService;

/**
 * @Author Prock.Liy
 * @Date 2020/10/26 21:45
 * @Description
 **/
@Slf4j
@Component
public class HelloServiceFallback implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable throwable) {
        return name -> {
            log.error("调用server-system服务出错", throwable);
            return "调用出错";
        };
    }
}
