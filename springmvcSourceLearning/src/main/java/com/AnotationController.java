package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnotationController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "Test";
    }


    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(User user){
        System.out.println(user);
        return user.toString();
    }



}
