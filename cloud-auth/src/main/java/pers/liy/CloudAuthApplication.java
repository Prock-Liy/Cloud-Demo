package pers.liy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import pers.liy.annotation.EnableCloudAuthExceptionHandler;
import pers.liy.annotation.EnableCloudLettuceRedis;
import pers.liy.annotation.EnableCloudServerProtect;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 21:58
 * @Description 启动类
 **/
@EnableDiscoveryClient
@SpringBootApplication
@EnableCloudLettuceRedis
@EnableCloudServerProtect
@MapperScan("pers.liy.mapper")
@EnableCloudAuthExceptionHandler
public class CloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAuthApplication.class, args);
    }

}
