package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.crm.dto.req.ResourceChannelPageReqDto;
import com.wanda.ffanad.crm.service.CrmResourceService;

/**
 * 添加频道模块
 */
@RestController
@RequestMapping("/resourceChannel")
@SessionAttributes("user")
public class ResourceChannelManagerController {

    @Autowired
    private CrmResourceService crmResourceService;

    /**
     * 分页数据
     * 
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getPage(@Valid @RequestBody ResourceChannelPageReqDto resourceChannelPageReqDto) {
        return new RestResult(crmResourceService.getResourcesByPage(resourceChannelPageReqDto));
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult saveResourceChanel(@RequestParam(required = true) Integer resId,
                                         @RequestParam(required = true) Integer channelId, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        return new RestResult(
                crmResourceService.saveResourceChannel(resId, channelId, account.getAccountEmail()) ? "保存成功" : "保存失败");
    }
}
