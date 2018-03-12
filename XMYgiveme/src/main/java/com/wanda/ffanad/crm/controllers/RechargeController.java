package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.RechargeInfoEntity;
import com.wanda.ffanad.base.error.AdAsserts;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.constants.OperationLogAction;
import com.wanda.ffanad.core.constants.OperationLogOperation;
import com.wanda.ffanad.core.constants.OperationLogPage;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.integration.finance.req.RechargeInfoReqDto;
import com.wanda.ffanad.crm.integration.finance.resp.RechargeInfoRespDto;
import com.wanda.ffanad.crm.integration.finance.service.RechargeInfoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    private Logger              logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private RechargeInfoService rechargeInfoService;

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(path = "/searchRecharge", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
    public PaginationBo<RechargeInfoRespDto> searchRecharge(RechargeInfoReqDto recharge,
                                                            @RequestParam(required = true, defaultValue = "1", value = "pageNo") int pageNo,
                                                            @RequestParam(required = true, defaultValue = "20", value = "pageSize") int pageSize,
                                                            HttpServletRequest req) {
        return rechargeInfoService.queryRecharge(recharge, pageNo, pageSize);
    }

    @RequestMapping(path = "/rechargeInc", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
    public RestResult rechargeIncrease(Long invoiceAmount, Long proceedsAmount, Integer accountId, HttpSession session,
                                       String accountEmail) {
        AdAsserts.notNull(invoiceAmount, "发票金额不能为空");
        AdAsserts.notNull(proceedsAmount, "实收金额不能为空");
        AdAsserts.notNull(accountId, "账户ID不能为空");
        AdAsserts.isTrue(!(invoiceAmount > 999999999 || proceedsAmount > 999999999), "录入金额超出大小限制");
        RechargeInfoEntity recharge = new RechargeInfoEntity();
        recharge.setInvoiceAmount(10000000 * invoiceAmount);
        recharge.setProceedsAmount(10000000 * proceedsAmount);
        recharge.setAccountId(accountId);
        recharge.setOperatorId(Integer.parseInt(String.valueOf(session.getAttribute(SystemConstant.SESSION_ACCOUNT_ID))));
        rechargeInfoService.recharge(recharge);
        operationLogService.addOperationLog(OperationLogPage.RECHARGE, OperationLogOperation.RECHARGE,
                (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL), OperationLogAction.RECHARGE, accountEmail,
                "面额【" + invoiceAmount + "】，" + "实收金额【" + proceedsAmount + "】");
        return new RestResult(FfanadStatus.S_OK, null);
    }

    @RequestMapping(path = "/rechargeUndo", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
    public RestResult rechargeUndo(@RequestBody RechargeInfoEntity recharge, HttpSession session) {
        recharge.setRevokerId(Integer.parseInt(String.valueOf(session.getAttribute(SystemConstant.SESSION_ACCOUNT_ID))));
        rechargeInfoService.cancelRecharge(recharge);
        return new RestResult(FfanadStatus.S_OK, null);
    }

    @ApiOperation(value = "查询充值对象")
    @RequestMapping(value = "/{rechargeId}", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
    public RestResult get(@PathVariable Integer rechargeId) {
        return new RestResult(FfanadStatus.S_OK, rechargeInfoService.get(rechargeId));
    }

}
