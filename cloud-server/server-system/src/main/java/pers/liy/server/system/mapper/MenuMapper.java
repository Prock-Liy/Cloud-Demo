package pers.liy.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.liy.entity.system.Menu;

import java.util.List;

/**
 * @Author Prock.Liy
 * @Date 2020/11/8 21:55
 * @Description 菜单信息
 **/
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户名查询权限信息
     *
     * @param username 用户名称
     * @return 权限信息
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 通过用户名查询菜单信息
     *
     * @param username 用户名
     * @return 菜单信息
     */
    List<Menu> findUserMenus(String username);
}
