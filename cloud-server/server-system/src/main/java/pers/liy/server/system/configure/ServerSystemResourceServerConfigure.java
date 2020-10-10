package pers.liy.server.system.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import pers.liy.handler.CloudAccessDeniedHandler;
import pers.liy.handler.CloudAuthExceptionEntryPoint;

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

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
