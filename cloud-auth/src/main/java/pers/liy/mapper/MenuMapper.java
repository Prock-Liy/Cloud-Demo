package pers.liy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.liy.entity.system.Menu;

import java.util.List;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 17:44
 * @Description
 **/
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户名查找用户信息
     * @param username 用户名
     * @return List<Menu>
     */
    List<Menu> findUserPermissions(String username);
}
