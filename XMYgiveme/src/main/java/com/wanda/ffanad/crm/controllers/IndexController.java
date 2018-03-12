/**
 * 
 */
package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.SecMenuPo;

/**
 * @author 姜涛
 *
 */
@RestController
public class IndexController {

    /**
     * 根地址登录后跳转到首页
     * 
     * @author 姜涛
     * @param session
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpSession session, HttpServletResponse response) throws IOException {
        String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
        if (StringUtils.isEmpty(accountEmail)) {
            response.sendRedirect("/login.html");
        } else {
            List<SecMenuPo> pList = (List<SecMenuPo>) session.getAttribute(SystemConstant.SESSION_ACCOUNT_MENUS);
            SecMenuPo firstMenu = pList.get(0);
            String url = firstMenu.getUrl();
            if (!firstMenu.getChildren().isEmpty()) {
                url = firstMenu.getChildren().get(0).getUrl();
            }
            response.sendRedirect(url);
        }
    }
}
