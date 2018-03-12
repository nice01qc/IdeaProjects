package com.wanda.ffanad.crm.controllers;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.Link;
import com.wanda.ffanad.core.services.LinkService;
import com.wanda.ffanad.crm.common.PaginationBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 创意链接相关服务的控制器.
 */
@RestController
@RequestMapping("/link")
@SessionAttributes("user")
public class LinkController {

    /**
     * 自动注入的{@link LinkService}的实例.
     */
    @Autowired
    private LinkService linkService;

    /**
     * 处理POST请求. 调用{@link LinkService#addNewLink(Link)}方法. 返回插入条目数.
     *
     * @param record 前台传来的数据
     * @return 返回前台的数据，成功插入的条目数
     */
    @RequestMapping(method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public final int addNewLink(@RequestBody Link record, HttpSession session) {
        String email = session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString();
        return linkService.addNewLink(record, email);
    }

    /**
     * 获取资源列表
     * 
     * @author tianhongbo
     * @return
     */
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public PaginationBo<Link> list(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(required = false, defaultValue = "20") int pageSize, Link record) {
        PaginationBo<Link> pbo = new PaginationBo<Link>();
        List<Link> list = linkService.getLinkPage(record, pageNo, pageSize);
        pbo.setRows(list);
        pbo.setTotal(linkService.getLinkPageTotal(record));
        return pbo;
    }
}
