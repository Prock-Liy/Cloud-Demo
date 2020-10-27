package pers.liy.server.test.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.liy.entity.CloudServerConstant;
import pers.liy.server.test.service.fallback.HelloServiceFallback;

/**
 * @Author Prock.Liy
 * @Date 2020/10/26 21:39
 * @Description
 **/
@FeignClient(value = CloudServerConstant.CLOUD_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}