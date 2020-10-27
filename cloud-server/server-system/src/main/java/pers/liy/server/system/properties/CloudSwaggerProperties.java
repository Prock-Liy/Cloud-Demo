package pers.liy.server.system.properties;

import lombok.Data;

/**
 * @Author Prock.Liy
 * @Date 2020/10/12 22:26
 * @Description
 **/
@Data
public class CloudSwaggerProperties {

    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;

    private String grantUrl;
    private String name;
    private String scope;
}
