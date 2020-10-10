package pers.liy.properties;

import lombok.Data;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 13:20
 * @Description  认证服务器配置类AuthorizationServerConfigure里使用硬编码的形式配置了client_id，client_secret等信息。硬编码的形式不利于代码维护和升级，所以我们需要将它改造为可配置的方式
 **/
@Data
public class ClientsProperties {

    /**
     * client_id
     */
    private String client;
    /**
     * client_secret
     */
    private String secret;
    /**
     * 令牌支持的认证类型
     */
    private String grantType = "password,authorization_code,refresh_token";
    /**
     * 认证范围
     */
    private String scope = "all";

}
