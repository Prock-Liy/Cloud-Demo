package pers.liy.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import pers.liy.constant.CloudConstant;
import pers.liy.entity.CloudResponse;
import pers.liy.properties.CloudGatewayProperties;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @Author: Prock.Liy
 * @Date: 2020/11/23
 * @Version: 1.0
 * @Description: 全局过滤器
 */
@Slf4j
@Component
public class CloudGatewayRequestFilter implements GlobalFilter {

    @Resource
    private CloudGatewayProperties properties;
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 通过ServerWebExchange对象获取到ServerHttpRequest请求和ServerHttpResponse响应对象。
     * 通过ServerHttpRequest的mutate方法可以修改请求，在请求头部添加了之前定义的Zuul网关密钥
     * （因为现在网关不再由Zuul构建，所以 这个常量的名字可以自己修改为别的，我这里就不修改了）。
     * 修改了ServerHttpRequest对象后，需要将它设置到ServerWebExchange对象中。同样的，
     * 调用ServerWebExchange的mutate方法来修改ServerWebExchange，然后将新
     * 的ServerWebExchange添加到GatewayFilterChain过滤器链中
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 禁止客户端的访问资源逻辑
        Mono<Void> checkForbidUriResult = checkForbidUri(request, response);
        if (checkForbidUriResult != null) {
            return checkForbidUriResult;
        }

        //日志打印
        printLog(exchange);

        byte[] token = Base64Utils.encode((CloudConstant.ZUUL_TOKEN_VALUE).getBytes());
        ServerHttpRequest build = request.mutate().header(CloudConstant.ZUUL_TOKEN_HEADER, new String(token)).build();
        ServerWebExchange newExchange = exchange.mutate().request(build).build();
        return chain.filter(newExchange);
    }

    /**
     * 定义过滤器的主要逻辑。这里我们通过请求上下文RequestContext获取了转发的服务名称serviceId和请求对象HttpServletRequest，
     * 并打印请求日志。随后往请求上下文的头部添加了Key为ZuulToken，Value为cloud:zuul:123456的信息。这两个值可以到常量类中修改
     * @return
     */
    private Mono<Void> checkForbidUri(ServerHttpRequest request, ServerHttpResponse response) {
        String uri = request.getPath().toString();
        boolean shouldForward = true;
        String forbidRequestUri = properties.getForbidRequestUri();
        String[] forbidRequestUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri, ",");
        if (forbidRequestUris != null && ArrayUtils.isNotEmpty(forbidRequestUris)) {
            for (String u : forbidRequestUris) {
                if (pathMatcher.match(u, uri)) {
                    shouldForward = false;
                }
            }
        }
        if (!shouldForward) {
            CloudResponse cloudResponse = new CloudResponse().message("该URI不允许外部访问");
            return makeResponse(response, cloudResponse);
        }
        return null;
    }

    private Mono<Void> makeResponse(ServerHttpResponse response, CloudResponse cloudResponse) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(cloudResponse).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 打印转发日志
     *
     * printLog方法的主要逻辑是：通过ServerWebExchange对象的getAttribute方法获取各种信息，比如请求URI信息，路由信息等。
     * 可用的key值可以查看ServerWebExchangeUtils类中的属性值：
     * @param exchange
     */
    private void printLog(ServerWebExchange exchange) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        LinkedHashSet<URI> uris = exchange.getAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originUri = null;
        if (uris != null) {
            originUri = uris.stream().findFirst().orElse(null);
        }
        if (url != null && route != null && originUri != null) {
            log.info("转发请求：{}://{}{} --> 目标服务：{}，目标地址：{}://{}{}，转发时间：{}",
                    originUri.getScheme(), originUri.getAuthority(), originUri.getPath(),
                    route.getId(), url.getScheme(), url.getAuthority(), url.getPath(), LocalDateTime.now()
            );
        }
    }
}
