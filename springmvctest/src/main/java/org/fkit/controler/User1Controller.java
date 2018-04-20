package org.fkit.controler;

import domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class User1Controller {

    @ModelAttribute
    public void userModel(String loginname, String password, Model model){

        User user = new User();
        user.setLoginname(loginname);
        user.setPassword(password);
        model.addAttribute("user",user);
    }

    @RequestMapping(value = "/login")
    public String login(Model model){
        User user = (User)model.asMap().get("user");
        System.out.println(user);
        user.setLoginname("nice");
        return "result";
    }

    @RequestMapping(value = "/requestHeaderTest")
    public void requestHeader(@RequestHeader("User-Agent") String userAgent, HttpServletRequest httpServletRequest){
        System.out.println(userAgent);
    }
}
