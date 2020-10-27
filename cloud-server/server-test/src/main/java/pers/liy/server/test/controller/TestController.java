package pers.liy.server.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.liy.server.test.service.IHelloService;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @Author Prock.Liy
 * @Date 2020/10/26 21:24
 * @Description
 **/
@RestController
public class TestController {

    @Resource
    private IHelloService helloService;

    @GetMapping("test1")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String test1(){
        return "拥有'user:add'权限";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public String test2(){
        return "拥有'user:update'权限";
    }

    @GetMapping("hello")
    public String hello(String name){
        return this.helloService.hello(name);
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }
}
