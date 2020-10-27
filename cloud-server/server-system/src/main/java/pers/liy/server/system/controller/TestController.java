package pers.liy.server.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author Prock.Liy
 * @Date 2020/10/10 12:30
 * @Description
 **/
@RestController
public class TestController {

    @GetMapping("hello")
    public String hello(String name) {
        return "hello" + name;
    }

    @GetMapping("info")
    public String test(){
        return "server-system";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

}
