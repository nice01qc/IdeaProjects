/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.common.Page;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.error.WrongParamException;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.crm.dto.req.AdxAlreadyReviewListReqVo;
import com.wanda.ffanad.crm.dto.req.AdxReviewOperateReqVo;
import com.wanda.ffanad.crm.dto.req.AdxWaitReviewListReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxReviewListResVo;
import com.wanda.ffanad.crm.service.AdxReviewManagerService;

/**
 * 类AdxReviewController.java的实现描述：adx 审核管理器
 * 
 * @author liuzhenkai1 2016年10月19日 下午4:59:07
 */
@RestController
@RequestMapping("/adxReviewManager")
public class AdxReviewManagerController {

    @Autowired
    private AdxReviewManagerService armService;

    /**
     * 查询待审核列表
     * 
     * @return
     */
    @RequestMapping(value = "/getWaitReviewList", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getWaitReviewList(AdxWaitReviewListReqVo arlv) {
        RestResult rest = new RestResult();
        //校验参数
        if (armService.checkWaitReviewListParams(arlv)) {
            Page<AdxReviewListResVo> page = armService.getWaitReviewListPage(arlv);
            rest.setResult(page);
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 获取所有的dsp名称
     * 
     * @return
     */
    @RequestMapping(value = "/getAllDsp", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getAllDsp() {
        RestResult rest = new RestResult();
        rest.setResult(armService.getAllDsp());
        return rest;
    }

    /**
     * 查询已审核列表
     * 
     * @return
     */
    @RequestMapping(value = "/getAlreadyReviewList", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getAlreadyReviewList(AdxAlreadyReviewListReqVo aarlv) {
        RestResult rest = new RestResult();
        //校验参数
        if (armService.checkAlreadyReviewListParams(aarlv)) {
            Page<AdxReviewListResVo> page = armService.getAlreadyReviewListPage(aarlv);
            rest.setResult(page);
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 操作审核(可批量)
     * 
     * @return
     */
    @RequestMapping(value = "/operateReview", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult operateReview(HttpSession session, AdxReviewOperateReqVo aror) {
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        aror.setAccount(accountEntity);
        RestResult rest = new RestResult();
        if (armService.checkOperateReviewParams(aror)) {
            try {
                ResultBo rv = armService.operateReview(aror);
                if (rv.getStatus() == Constant.ResultStatus.RESULT_STATUS_FAILED) {
                    rest.setStatus(Constant.Http.ERROR);
                    rest.setMessage(rv.getMessage());
                }
            } catch (WrongParamException we) {
                rest.setStatus(Constant.Http.ERROR);
                rest.setMessage(Constant.TipString.CHECK_FAILED);
            }
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }

    /**
     * 根据ID获取创意
     * 
     * @return
     */
    @RequestMapping(value = "/getCreationReviewById", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getCreationReviewById(String id) {
        RestResult rest = new RestResult();
        if (StringUtils.isNotBlank(id) && id.matches(Constant.CommonReg.NUMBER)) {
            rest.setResult(armService.getCreationReviewById(id));
        } else {
            rest.setStatus(Constant.Http.ERROR);
            rest.setMessage(Constant.TipString.CHECK_FAILED);
        }
        return rest;
    }
}
