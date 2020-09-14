package pers.liy.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import pers.liy.service.UserDetailService;

import javax.annotation.Resource;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:37
 * @Description  @EnableAuthorizationServer 注解标注，开启认证服务器相关配置
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private UserDetailService userDetailService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("cloud")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all");
    }

    /**
     * 1.客户端从认证服务器获取令牌的时候，必须使用client_id为cloud，client_secret为123456的标识来获取；
     * 2.该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌；
     * 2.在获取client_id为cloud的令牌的时候，scope只能指定为all，否则将获取失败；
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices());
    }

    /**
     * tokenStore使用的是RedisTokenStore，认证服务器生成的令牌将被存储到Redis
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 指定了令牌的基本配置，比如令牌有效时间为60 * 60 * 24秒，刷新令牌有效时间为60 * 60 * 24 * 7秒，
     * setSupportRefreshToken设置为true表示开启刷新令牌的支持
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24);
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenServices;
    }

}
