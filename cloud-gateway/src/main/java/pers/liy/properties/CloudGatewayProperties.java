package pers.liy.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Prock.Liy
 * @Date: 2020/11/23
 * @Version: 1.0
 * @Description: 定义禁止客户端访问资源
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cloud-gateway.properties"})
@ConfigurationProperties(prefix = "cloud.gateway")
public class CloudGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;
}
