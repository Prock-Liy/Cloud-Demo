package pers.liy.server.system.configure;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import pers.liy.handler.CloudAccessDeniedHandler;
import pers.liy.handler.CloudAuthExceptionEntryPoint;
import pers.liy.server.system.properties.CloudServerSystemProperties;

import javax.annotation.Resource;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 12:25
 * @Description
 **/
@Configuration
@EnableResourceServer
public class ServerSystemResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Resource
    private CloudAccessDeniedHandler accessDeniedHandler;
    @Resource
    private CloudAuthExceptionEntryPoint exceptionEntryPoint;
    @Resource
    private CloudServerSystemProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 免认证路径
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
