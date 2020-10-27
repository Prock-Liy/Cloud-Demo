package pers.liy.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.liy.entity.CloudAuthUser;
import pers.liy.entity.system.SystemUser;

import javax.annotation.Resource;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:42
 * @Description 用于校验用户名密码的类
 **/
@Service
public class UserDetailService implements UserDetailsService {

    @Resource
    private IUserService iUserService;

    /**
     * 返回一个UserDetails对象，该对象也是一个接口，包含一些用于描述用户信息的方法
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = iUserService.findByName(username);
        if (iUserService != null) {
            String permissions = iUserService.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())) {
                notLocked = true;
            }
            CloudAuthUser authUser = new CloudAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser, authUser);
            return authUser;
        } else {
            throw new UsernameNotFoundException("");
        }
    }
}
