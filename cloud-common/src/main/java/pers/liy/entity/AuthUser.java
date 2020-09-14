package pers.liy.entity;

import lombok.Data;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:47
 * @Description
 **/
@Data
public class AuthUser {

    private static final long serialVersionUID = -1748289340320186418L;

    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;


}
