package pers.liy.server.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.liy.entity.CloudResponse;
import pers.liy.entity.QueryRequest;
import pers.liy.entity.system.SystemUser;
import pers.liy.exception.CloudException;
import pers.liy.server.system.service.IUserService;
import pers.liy.utils.CloudUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 22:35
 * @Description
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    public CloudResponse userList(QueryRequest queryRequest, SystemUser user) {
        Map<String, Object> dataTable = CloudUtil.getDataTable(userService.findUserDetail(user, queryRequest));
        return new CloudResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    public void addUser(@Valid SystemUser user, BindingResult bindingResult) throws CloudException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new CloudException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('user:update')")
    public void updateUser(@Valid SystemUser user) throws CloudException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new CloudException(message);
        }
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws CloudException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new CloudException(message);
        }
    }

//    @GetMapping("index")
//    public CloudException index() {
//        Map<String, Object> data = new HashMap<>(5);
//        // 获取系统访问记录
//        Long totalVisitCount = loginLogService.findTotalVisitCount();
//        data.put("totalVisitCount", totalVisitCount);
//        Long todayVisitCount = loginLogService.findTodayVisitCount();
//        data.put("todayVisitCount", todayVisitCount);
//        Long todayIp = loginLogService.findTodayIp();
//        data.put("todayIp", todayIp);
//        // 获取近期系统访问记录
//        List<Map<String, Object>> lastTenVisitCount = loginLogService.findLastTenDaysVisitCount(null);
//        data.put("lastTenVisitCount", lastTenVisitCount);
//        SystemUser param = new SystemUser();
//        param.setUsername(CloundUtil.getCurrentUsername());
//        List<Map<String, Object>> lastTenUserVisitCount = loginLogService.findLastTenDaysVisitCount(param);
//        data.put("lastTenUserVisitCount", lastTenUserVisitCount);
//        return new CloudException().data(data);
//    }
}
