package pers.liy.validator;

import org.apache.commons.lang3.StringUtils;
import pers.liy.annotation.IsMobile;
import pers.liy.constant.RegexpConstant;
import pers.liy.utils.CloudUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 22:45
 * @Description 参数校验逻辑
 **/
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return CloudUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
