package pers.liy.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.liy.entity.router.VueRouter;
import pers.liy.entity.system.Menu;

import java.util.List;
import java.util.Set;

/**
 * @Author Prock.Liy
 * @Date 2020/11/8 21:57
 * @Description
 **/
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户名查询用户权限信息
     *
     * @param username 用户名
     * @return 权限信息
     */
    Set<String> findUserPermissions(String username);

    /**
     * 通过用户名创建对应的 Vue路由信息
     *
     * @param username 用户名
     * @return 路由信息
     */
    List<VueRouter<Menu>> getUserRouters(String username);
}
