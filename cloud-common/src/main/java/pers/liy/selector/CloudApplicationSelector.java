package pers.liy.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.liy.configure.CloudAuthExceptionConfigure;
import pers.liy.configure.CloudOAuth2FeignConfigure;
import pers.liy.configure.CloudServerProtectConfigure;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 16:28
 * @Description 一次加载自定义注解
 **/
public class CloudApplicationSelector implements ImportSelector {

    /**
     * 通过selectImports方法，我们一次性导入了CloudAuthExceptionConfigure、CloudServerProtectConfigure
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                CloudAuthExceptionConfigure.class.getName(),
                CloudOAuth2FeignConfigure.class.getName(),
                CloudServerProtectConfigure.class.getName()
        };
    }

}
