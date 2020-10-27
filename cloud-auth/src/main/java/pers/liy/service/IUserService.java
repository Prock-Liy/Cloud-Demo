package pers.liy.service;

import org.springframework.stereotype.Service;
import pers.liy.entity.system.Menu;
import pers.liy.entity.system.SystemUser;
import pers.liy.mapper.MenuMapper;
import pers.liy.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 17:57
 * @Description
 **/
@Service
public class IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;

    public SystemUser findByName(String username) {
        return userMapper.findByName(username);
    }

    public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

}
