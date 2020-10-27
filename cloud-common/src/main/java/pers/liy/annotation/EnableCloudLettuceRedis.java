package pers.liy.annotation;

import org.springframework.context.annotation.Import;
import pers.liy.configure.CloudLettuceRedisConfigure;

import java.lang.annotation.*;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 21:09
 * @Description 驱动CloudLettuceRedisConfigure到spring
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudLettuceRedisConfigure.class)
public @interface EnableCloudLettuceRedis {
}
