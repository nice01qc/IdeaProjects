package org.fkit.controler;

import com.alibaba.fastjson.JSONObject;
import domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/json")
public class ModelAttribute1Controller {


    @RequestMapping(value = "/testRequestBody")
    public void lorin1(@RequestBody Book book, HttpServletResponse response) throws IOException {

        System.out.println(book);

        book.setAuthor("lijinhhhh");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSONObject.toJSONString(book));

    }

}