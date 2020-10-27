package pers.liy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import pers.liy.constant.CloudConstant;
import pers.liy.entity.CloudResponse;
import pers.liy.properties.CloudGatewayProperties;
import pers.liy.utils.CloudUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 16:04
 * @Description
 **/
@Slf4j
@Component
public class CloudGatewayRequestFilter extends ZuulFilter {

    @Resource
    private CloudGatewayProperties properties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 对应Zuul生命周期的四个阶段：pre、post、route和error，我们要在请求转发出去前添加请求头，所以这里指定为pre；
     * @return FilterConstants.PRE_TYPE
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的优先级，数字越小，优先级越高。PreDecorationFilter过滤器的优先级为5，所以我们可以指定为6让我们的过滤器优先级比它低
     * @return 6
     */
    @Override
    public int filterOrder() {
        return 6;
    }

    /**
     * 方法返回boolean类型，true时表示是否执行该过滤器的run方法，false则表示不执行；
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 定义过滤器的主要逻辑。这里我们通过请求上下文RequestContext获取了转发的服务名称serviceId和请求对象HttpServletRequest，
     * 并打印请求日志。随后往请求上下文的头部添加了Key为ZuulToken，Value为cloud:zuul:123456的信息。这两个值可以抽取到常量类中
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        HttpServletRequest request = ctx.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("请求URI：{}，HTTP Method：{}，请求IP：{}，ServerId：{}", uri, method, host, serviceId);

        // 禁止外部访问资源实现
        boolean shouldForward = true;
        // 从配置文件里读取到禁止外部访问资源的链接，并以逗号分隔转换为数组,遍历这些数组，逐一判断客户端请求URI是否在禁止外部访问的范围内，如果是的话则返回“该URI不允许外部访问”响应，状态码为403，不是的话放行
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
            HttpServletResponse response = ctx.getResponse();
            CloudResponse cloudResponse = new CloudResponse().message("该URI不允许外部访问");
            try {

                CloudUtil.makeResponse(
                        response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                        HttpServletResponse.SC_FORBIDDEN, cloudResponse
                );
                ctx.setSendZuulResponse(false);
                ctx.setResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        byte[] token = Base64Utils.encode((CloudConstant.ZUUL_TOKEN_VALUE).getBytes());
        ctx.addZuulRequestHeader(CloudConstant.ZUUL_TOKEN_HEADER, new String(token));
        return null;
    }

}
