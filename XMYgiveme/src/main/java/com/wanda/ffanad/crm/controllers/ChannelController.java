package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wanda.ffanad.core.common.FfanadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.wanda.ffanad.crm.dto.req.ChannelSaveReqDto;
import com.wanda.ffanad.crm.service.ChannelService;

/**
 * 添加频道模块
 */
@RestController
@RequestMapping("/channel")
@SessionAttributes("user")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * 频道分页数据
     * 
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return new RestResult(channelService.getChannelPage(page, limit));
    }

    /**
     * 所有频道
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getAll() {
        return new RestResult(channelService.getAllChannel());
    }

    /**
     * 保存频道(新增/更新)
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult saveChanel(@Valid @RequestBody ChannelSaveReqDto channelSaveReqDto, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        boolean result = channelService.save(channelSaveReqDto, account.getAccountEmail());
        return new RestResult(FfanadStatus.S_OK, result ? "保存成功" : "保存失败");
    }

    /**
     * 删除频道
     * 
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult delete(@PathVariable("channelId") Integer channelId, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        boolean result = channelService.delete(channelId, account.getAccountEmail());
        return new RestResult(FfanadStatus.S_OK, result ? "删除成功" : "删除失败");
    }
}
