package pers.liy;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Prock.Liy
 * @Date 2020/10/14 12:15
 * @Description 启动类
 * @EnableAdminServer注解标注，开启Spring Boot Admin服务端功能
 **/
@EnableAdminServer
@SpringBootApplication
public class CloudMonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMonitorAdminApplication.class, args);
    }

}
