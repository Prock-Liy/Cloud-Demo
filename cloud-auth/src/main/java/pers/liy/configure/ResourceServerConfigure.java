package pers.liy.configure;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import pers.liy.handler.CloudAccessDeniedHandler;
import pers.liy.handler.CloudAuthExceptionEntryPoint;
import pers.liy.properties.CloudAuthProperties;

import javax.annotation.Resource;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:33
 * @Description 用于处理非/oauth/开头的请求，其主要用于资源的保护，
 * 客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Resource
    private CloudAccessDeniedHandler accessDeniedHandler;
    @Resource
    private CloudAuthExceptionEntryPoint exceptionEntryPoint;
    @Resource
    private CloudAuthProperties properties;

    /**
     * 通过requestMatchers().antMatchers("/**")的配置表明该安全配置对所有请求都生效。
     * 类上的@EnableResourceServer用于开启资源服务器相关配置。
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                // 免SBA认证
                .antMatchers("/actuator/**").permitAll()
                // 免认证资源
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}
