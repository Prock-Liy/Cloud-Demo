package pers.liy.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Prock.Liy
 * @Date 2020/10/12 22:27
 * @Description
 **/
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cloud-server-system.properties"})
@ConfigurationProperties(prefix = "cloud.server.system")
public class CloudServerSystemProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private CloudSwaggerProperties swagger = new CloudSwaggerProperties();
}
