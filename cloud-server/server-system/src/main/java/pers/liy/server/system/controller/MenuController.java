package pers.liy.server.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.liy.entity.CloudResponse;
import pers.liy.entity.router.VueRouter;
import pers.liy.entity.system.Menu;
import pers.liy.server.system.service.IMenuService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author Prock.Liy
 * @Date 2020/11/8 22:01
 * @Description 前端路由控制
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("/{username}")
    public CloudResponse getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> result = new HashMap<>(3);
        // 构建用户路由对象
        List<VueRouter<Menu>> userRouters = this.menuService.getUserRouters(username);
        // 获取用户权限信息
        Set<String> userPermissions = this.menuService.findUserPermissions(username);
        // 组装数据
        result.put("routes", userRouters);
        result.put("permissions", userPermissions);
        return new CloudResponse().data(result);
    }
}
