package pers.liy.annotation;

import org.springframework.context.annotation.Import;
import pers.liy.configure.CloudOAuth2FeignConfigure;

import java.lang.annotation.*;

/**
 * @author Prock.Liy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudOAuth2FeignConfigure.class)
public @interface EnableCloudOauth2FeignClient {
}
