package pers.liy.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.liy.constant.CloudConstant;
import pers.liy.entity.CloudResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 16:10
 * @Description  全局拦截器拦截请求，并校验Zuul Token
 **/
public class CloudServerProtectInterceptor implements HandlerInterceptor {

    /**
     * HandlerInterceptor的preHandle方法，该拦截器可以拦截所有Web请求
     * @param request  获取请求头中的Zuul Token
     * @param response
     * @param handler
     * @return
     * @throws IOException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 从请求头中获取 Zuul Token
        String token = request.getHeader(CloudConstant.ZUUL_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(CloudConstant.ZUUL_TOKEN_VALUE.getBytes()));
        // 校验 Zuul Token的正确性
        if (StringUtils.equals(zuulToken, token)) {
            return true;
        } else {
            CloudResponse cloudResponse = new CloudResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(JSONObject.toJSONString(cloudResponse.message("请通过网关获取资源")));
            return false;
        }
    }

}
