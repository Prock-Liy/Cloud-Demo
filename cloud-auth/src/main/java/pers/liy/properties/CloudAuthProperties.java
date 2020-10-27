package pers.liy.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 13:24
 * @Description
 **/
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cloud-auth.properties"})
@ConfigurationProperties(prefix = "cloud.auth")
public class CloudAuthProperties {

    /**
     * 因为一个认证服务器可以根据多种Client来发放对应的令牌，所以这个属性使用的是数组形式
     */
    private ClientsProperties[] clients = {};
    /**
     * 用于指定access_token的有效时间，默认值为60 * 60 * 24秒
     */
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    /**
     * 用于指定refresh_token的有效时间，默认值为60 * 60 * 24 * 7秒
     */
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;
    /**
     * 免认证路径
     */
    private String anonUrl;

    /**
     * 验证码配置类
     */
    private CloudValidateCodeProperties code = new CloudValidateCodeProperties();
}