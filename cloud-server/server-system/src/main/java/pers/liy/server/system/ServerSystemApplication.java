package pers.liy.server.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pers.liy.annotation.CloudApplication;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 12:15
 * @Description 启动类
 * @EnableGlobalMethodSecurity(prePostEnabled = true)注解，表示开启Spring Cloud Security权限注解
 **/
@CloudApplication
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("pers.liy.server.system.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSystemApplication.class, args);
    }

}
