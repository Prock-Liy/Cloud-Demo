package pers.liy.annotation;

import pers.liy.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 22:44
 * @Description
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsMobile {


    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
