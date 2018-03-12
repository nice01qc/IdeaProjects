/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;

import com.wanda.ffanad.base.message.CacheEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.common.Page;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.crm.dto.req.AdxDspDetailReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspPageReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspStatusReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspDetailResVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspListResVo;
import com.wanda.ffanad.crm.service.AdxDspManagerService;

/**
 * 类AdxDspManagerController.java的实现描述：ADX dsp控制器
 *
 * @author liuzhenkai1 2016年10月19日 下午4:57:23
 */
@RestController
@RequestMapping("/adxDspManager")
public class AdxDspManagerController {

    @Autowired
    private AdxDspManagerService admService;

    @Autowired
    private CacheEventDispatcher cacheEventDispatcher;

    /**
     * 新增dsp
     *
     * @return
     */
    @RequestMapping(value = "/addDsp", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult addDsp(HttpSession session, AdxDspDetailReqVo addv) {
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        addv.setAccount(accountEntity);
        RestResult rest = new RestResult();
        //校验参数
        if (admService.checkAddDspParams(addv)) {
            ResultBo rv = admService.addDsp(addv);
            if (rv.getStatus() == Constant.ResultStatus.RESULT_STATUS_FAILED) {
                rest.setStatus(Constant.Http.ERROR);
                rest.setMessage(rv.getMessage());
            }else {
                cacheEventDispatcher.dispatchDspEntity(Long.parseLong(addv.getId()));
            }
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 查询dsp列表
     *
     * @return
     */
    @RequestMapping(value = "/getDspList", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getDspList(AdxDspPageReqVo adpv) {
        RestResult rest = new RestResult();
        //校验参数
        if (admService.checkAdxDspPageParam(adpv)) {
            Page<AdxDspListResVo> page = admService.getDspPage(adpv);
            rest.setResult(page);
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 根据ID查询dsp信息
     *
     * @return
     */
    @RequestMapping(value = "/getDspById", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getDspById(AdxDspStatusReqVo adsv) {
        RestResult rest = new RestResult();
        AdxDspDetailResVo adrv = admService.getDspById(adsv);
        rest.setResult(adrv);
        return rest;
    }

    /**
     * 更新dsp
     *
     * @return
     */
    @RequestMapping(value = "/modifyDspInfo", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult modifyDspInfo(HttpSession session, AdxDspDetailReqVo addv) {
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        addv.setAccount(accountEntity);
        RestResult rest = new RestResult();
        //校验参数
        if (admService.checkModifyDspParams(addv)) {
            ResultBo rv = admService.modifyDsp(addv);
            if (rv.getStatus() == Constant.ResultStatus.RESULT_STATUS_FAILED) {
                rest.setStatus(Constant.Http.ERROR);
                rest.setMessage(rv.getMessage());
            }
            cacheEventDispatcher.dispatchDspEntity(Long.parseLong(addv.getId()));
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 更新dsp token
     *
     * @return
     */
    @RequestMapping(value = "/updateDspToken", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult updateDspToken(HttpSession session, AdxDspStatusReqVo adsv) {
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        adsv.setAccount(accountEntity);
        RestResult rest = new RestResult();
        ResultBo rv = admService.updateDspToken(adsv);
        if (rv.getStatus() == Constant.ResultStatus.RESULT_STATUS_FAILED) {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(rv.getMessage());
        }
        return rest;
    }

    /**
     * 更新dsp token
     *
     * @return
     */
    @RequestMapping(value = "/updateDspStatus", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult updateDspStatus(HttpSession session, AdxDspStatusReqVo adsv) {
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        adsv.setAccount(accountEntity);
        RestResult rest = new RestResult();
        ResultBo rv = admService.updateDspStatus(adsv);
        cacheEventDispatcher.dispatchDspEntity(Long.parseLong(adsv.getId()));
        if (rv.getStatus() == Constant.ResultStatus.RESULT_STATUS_FAILED) {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(rv.getMessage());
        }
        return rest;
    }

}
