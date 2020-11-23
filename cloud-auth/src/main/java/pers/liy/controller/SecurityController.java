package pers.liy.controller;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.liy.entity.CloudResponse;
import pers.liy.exception.ValidateCodeException;
import pers.liy.service.ValidateCodeService;

import javax.annotation.Resource;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:48
 * @Description 对外提供一些REST服务
 **/
@RestController
public class SecurityController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;
    @Resource
    private ValidateCodeService validateCodeService;

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    /**
     * signout方法通过ConsumerTokenServices来注销当前Token
     * @param request HttpServletRequest
     * @return Response
     * @throws AuthException
     */
    @DeleteMapping("signout")
    public CloudResponse signout(HttpServletRequest request) throws AuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        CloudResponse response = new CloudResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new AuthException("退出登录失败");
        }
        return response.message("退出登录成功");
    }

    /**
     * 客户端调用生成验证码
     * @param request
     * @param response
     * @throws IOException
     * @throws ValidateCodeException
     */
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }
}
