package com;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AbstractController {
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<User> list = new ArrayList<User>();
        User u1 = new User("Jake",22);
        User u2 = new User("Kang",11);
        list.add(u1);
        list.add(u2);
        return new ModelAndView("userlist","users",list);
    }
}
