package com.baobaotao.web;

import com.baobaotao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

public class LoginController {

    private UserService userService;
    
    @RequestMapping(value = "/index.html")

    public String loginPage(){
        return "login";
    }

}
