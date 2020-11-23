package pers.liy.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @Author: Prock.Liy
 * @Date: 2020/11/23
 * @Version: 1.0
 * @Description:    跨域设置
 */
@Configuration
public class CloudGateWayCorsConfigure {

    /**
     * Gateway不是基于Servlet，而是基于Reactive编程模型的WebFlux
     *
     * setAllowCredentials(true)表示允许cookie跨域；
     * addAllowedHeader(CorsConfiguration.ALL)表示请求头部允许携带任何内容；
     * addAllowedOrigin(CorsConfiguration.ALL)表示允许任何来源；
     * addAllowedMethod(CorsConfiguration.ALL)表示允许任何HTTP方法
     * @return CorsFilter
     */
    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin(CorsConfiguration.ALL);
        cors.addAllowedHeader(CorsConfiguration.ALL);
        cors.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", cors);
        return new CorsWebFilter(source);
    }

}
