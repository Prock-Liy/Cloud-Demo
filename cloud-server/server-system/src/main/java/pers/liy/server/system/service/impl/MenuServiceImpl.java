package pers.liy.server.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.liy.entity.router.RouterMeta;
import pers.liy.entity.router.VueRouter;
import pers.liy.entity.system.Menu;
import pers.liy.server.system.mapper.MenuMapper;
import pers.liy.server.system.service.IMenuService;
import pers.liy.utils.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Prock.Liy
 * @Date 2020/11/8 21:58
 * @Description
 **/
@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public Set<String> findUserPermissions(String username) {
        List<Menu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    @Override
    public List<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.baseMapper.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon()));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }
}
