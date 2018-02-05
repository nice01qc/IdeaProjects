package com.nice.springmvc.web.ch4_3;

import com.nice.springmvc.domain.DemoObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/anno")
public class DemoAnnoController {

    @RequestMapping(produces = "text/plain;charset=UTF-8")
    public @ResponseBody String index(HttpServletRequest request, DemoObj obj, String nier){
        return "url:"+request.getRequestURI() + " can access" + " obj.name:" +
                obj.getName() + " obj.id :" + obj.getId();
    }

    
}
