package pers.liy.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.liy.service.UserDetailService;

import javax.annotation.Resource;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:29
 * @Description SecurityConfigure用于处理/oauth开头的请求，Spring Cloud OAuth内部定义的获取令牌，刷新令牌的请求地址都是以/oauth/开头的
 *              也就是说SecurityConfigure用于处理和令牌相关的请求
 **/
@Order(2)
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailService userDetailService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 密码模式需要使用到这个Bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 在Eureka.configure.SecurityConfigure类中，我们还重写了WebSecurityConfigurerAdapter类的
     * configure(HttpSecurity http)方法，
     * 其中requestMatchers().antMatchers("/oauth/**")的含义是：CloudSecurityConfigure安全配置类只对/oauth/开头的请求有效
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
}
