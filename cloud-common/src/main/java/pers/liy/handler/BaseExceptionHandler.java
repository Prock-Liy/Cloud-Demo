package pers.liy.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pers.liy.entity.CloudResponse;
import pers.liy.exception.AuthException;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 14:15
 * @Description   全局异常捕获
 **/
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CloudResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new CloudResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CloudResponse handleCloudAuthException(AuthException e) {
        log.error("系统错误", e);
        return new CloudResponse().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CloudResponse handleAccessDeniedException(){
        return new CloudResponse().message("没有权限访问该资源");
    }
}
