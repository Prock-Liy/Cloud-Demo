package pers.liy.server.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import pers.liy.annotation.EnableCloudAuthExceptionHandler;
import pers.liy.annotation.EnableCloudOauth2FeignClient;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 12:15
 * @Description 启动类
 * @EnableGlobalMethodSecurity(prePostEnabled = true)注解，表示开启Spring Cloud Security权限注
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableCloudOauth2FeignClient
@EnableCloudAuthExceptionHandler
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerTestApplication.class, args);
    }

}
