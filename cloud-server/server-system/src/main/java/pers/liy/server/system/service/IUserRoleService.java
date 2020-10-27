package pers.liy.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.liy.entity.system.UserRole;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 22:34
 * @Description
 **/
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);
}
