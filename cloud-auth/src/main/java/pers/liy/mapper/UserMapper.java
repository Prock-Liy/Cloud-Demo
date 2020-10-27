package pers.liy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.liy.entity.system.SystemUser;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 17:43
 * @Description
 **/
public interface UserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据用户名查询用户详情
     * @param username  用户名
     * @return SystemUser
     */
    SystemUser findByName(String username);

}
