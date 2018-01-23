package springmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.domain.User;

@Controller
@RequestMapping("/user")
public class UserControler {

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createUser(User user){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/createSuccess");
        mav.addObject("user",user);
        System.out.println(user);
        return mav;
    }

    @RequestMapping("/register")
    public String register(){
        return "user/register";
    }


}
