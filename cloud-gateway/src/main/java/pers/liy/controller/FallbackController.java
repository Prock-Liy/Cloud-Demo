package pers.liy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pers.liy.entity.CloudResponse;
import reactor.core.publisher.Mono;

/**
 * @Author: Prock.Liy
 * @Date: 2020/11/23
 * @Version: 1.0
 * @Description: 处理回退Collector
 */
@RestController
public class FallbackController {

    @RequestMapping("fallback/{name}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<CloudResponse> systemFallback(@PathVariable String name) {
        String response = String.format("访问%s超时或者服务不可用了", name);
        return Mono.just(new CloudResponse().message(response));
    }
}
