package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.services.security.bo.SecPermissionBo;

/**
 * 系统资源JavaScript对象
 * 
 * @author 姜涛
 *
 */
@RestController
public class SysResourceController {

    private Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    private long startTime;

    SysResourceController() {
        startTime = System.currentTimeMillis();
    }

    /**
     * 获取系统资源 如code等
     * 
     * @author 姜涛 2016年5月10日
     * @param req
     * @param res
     */
    @RequestMapping(value = "/sysResource", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_ALL })
    public void list(HttpServletRequest req, HttpServletResponse res) {
        String modTimeString = req.getHeader("If-Modified-Since");
        if (modTimeString != null && modTimeString.length() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            try {
                long ifModSince = format.parse(modTimeString).getTime();
                if (ifModSince == startTime) {
                    res.setContentType("application/javascript; charset=utf-8");
                    res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }
            } catch (Throwable e) {

            }
        }
        OutputStream out = null;
        try {
            out = res.getOutputStream();
            // write resource header
            res.setDateHeader("Last-Modified", startTime);
            res.setContentType("application/javascript; charset=utf-8");

            // write resource
            out.write("var sysCode=".getBytes("UTF-8"));



//            ObjectMapper mapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder();
//            Map<String, Object> map = parseJson(codeTree);
//            sb.append(mapper.writeValueAsString(map));
            // FIXME 临时
            sb.append("{}");

            out.write(sb.toString().getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            logger.warn("IOException while dump messages: " + e.getMessage(), e);
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 转成json
     * 
     * @author 姜涛 2016年5月10日
     * @param codeTree
     * @return
     */
//    private Map<String, Object> parseJson(List<SysCodeBo> codeTree) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        for (SysCodeBo aCode : codeTree) {
//            ObjectNode node = JsonNodeFactory.instance.objectNode();
//            node.put("name", aCode.getName());
//            if (!aCode.getChildren().isEmpty()) {
//                node.putPOJO("children", this.parseJson(aCode.getChildren()));
//            }
//            map.put(aCode.getCode(), node);
//        }
//        return map;
//    }

    /**
     * 获取登录用户授权菜单
     * 
     * @author 姜涛 2016年5月10日
     * @param session
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getMenus", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_ALL })
    public void getMenus(HttpSession session, HttpServletResponse res) {
        OutputStream out = null;
        try {
            out = res.getOutputStream();
            // write resource header
            res.setDateHeader("Last-Modified", startTime);
            res.setContentType("application/javascript; charset=utf-8");

            // write resource
            out.write("var menus=".getBytes("UTF-8"));
            List<SecPermissionBo> pList =
                    (List<SecPermissionBo>) session.getAttribute(SystemConstant.SESSION_ACCOUNT_MENUS);
            if (null == pList) {
                pList = new ArrayList<SecPermissionBo>();
            }

            ObjectMapper mapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder();
            sb.append(mapper.writeValueAsString(pList));
            out.write(sb.toString().getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            logger.warn("IOException while dump messages: " + e.getMessage(), e);
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
