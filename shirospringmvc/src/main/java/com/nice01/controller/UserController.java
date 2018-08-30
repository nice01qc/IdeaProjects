package com.nice01.controller;

import com.nice01.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST)
    @ResponseBody
    public String subLogin(User user){
        System.out.println(user);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try{
            subject.login(token);
            subject.checkRole("admin");
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return "login success...";

    }



    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRoles(){
        return "TestRole success";
    }


    @RequestMapping(value = "/testRoles1", method = RequestMethod.GET)
    @ResponseBody
    public String testRoles1(){
        return "testRoles1 success";
    }
}
