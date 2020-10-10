package pers.liy.filter;

import com.netflix.zuul.context.RequestContext;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import pers.liy.entity.CloudResponse;
import pers.liy.utils.CloudUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 14:11
 * @Description
 **/
@Slf4j
@Component
public class CloudGatewayErrorFilter extends SendErrorFilter {

    /**
     * 通过RequestContext获取到当前请求上下文，
     * 通过请求上下文可以获取到当前请求的服务名称serviceId和当前请求的异常对象ExceptionHolder等信息
     * @return
     */
    @Override
    public Object run() {
        try {
            CloudResponse cloudResponse = new CloudResponse();
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            cloudResponse = resolveExceptionMessage(message, serviceId, cloudResponse);

            HttpServletResponse response = ctx.getResponse();
            CloudUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cloudResponse
            );
            log.error("Zull sendError：{}", cloudResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private CloudResponse resolveExceptionMessage(String message, String serviceId, CloudResponse cloudResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return cloudResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return cloudResponse.message(serviceId + "服务不可用");
        }
        return cloudResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}
