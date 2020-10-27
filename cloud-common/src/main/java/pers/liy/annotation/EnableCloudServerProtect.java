package pers.liy.annotation;

import org.springframework.context.annotation.Import;
import pers.liy.configure.CloudServerProtectConfigure;

import java.lang.annotation.*;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 16:14
 * @Description  驱动它CloudServerProtectInterceptor
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudServerProtectConfigure.class)
public @interface EnableCloudServerProtect {
}
