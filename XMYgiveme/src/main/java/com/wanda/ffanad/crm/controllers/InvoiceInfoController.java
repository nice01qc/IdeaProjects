/**
 * 
 */
package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.InvoiceInfoEntity;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.constants.OperationLogOperation;
import com.wanda.ffanad.core.constants.OperationLogPage;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.integration.finance.service.InvoiceInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 姜涛
 *
 */
@Api(tags = { "发票相关接口" })
@RestController
@RequestMapping(value = "/finance/invoice")
public class InvoiceInfoController {

	@Autowired
    private InvoiceInfoService invoiceInfoService;
	
	@Autowired
    private OperationLogService operationLogService;

	/**
	 * 修改发票申请数据
	 * 
	 * @author 姜涛 2016年4月27日
	 * @param bo
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "修改发票充值状态")
	@RequestMapping(method = RequestMethod.PUT)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
	public RestResult update(Integer invoiceId, HttpSession session) {
	    invoiceInfoService.confirmInvoice(invoiceId);
	    AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
            operationLogService.addOperationLog(OperationLogPage.INVOICE_INFO, OperationLogOperation.CONFIRM_INVOICE,
                    account.getAccountEmail(), OperationLogPage.CONFIRM_INVOICE_INFO, invoiceId.toString(), "确认发票内容");
		return new RestResult(FfanadStatus.S_OK, null);
	}

	/**
	 * 修改发票快递信息
	 * 
	 * @author 姜涛 2016年6月13日
	 * @param bo
	 * @return
	 */
	@ApiOperation(value = "修改发票快递信息")
	@RequestMapping(value = "/express", method = RequestMethod.POST)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
	public RestResult updateInvoice(InvoiceInfoEntity entity, HttpSession session) {
	    invoiceInfoService.sendInvoice(entity);
	    //记录日志
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        operationLogService.addOperationLog(OperationLogPage.INVOICE_INFO, OperationLogOperation.SEND_OFF_INVOICE,
                account.getAccountEmail(), OperationLogPage.SEND_OFF_INVOICE, entity.getInvoiceId().toString(),
                "快递公司：" + entity.getExpressCompanyName() + "运单号：" + entity.getExpressNumber());
		return new RestResult(FfanadStatus.S_OK, null);
	}

	/**
	 * 
	 * 根据充值id获得发票json
	 * 
	 * @param id
	 * @return
	 * @author: 姜涛
	 */
	@ApiOperation(value = "根据充值id获得发票json")
	@RequestMapping(value = "/recharge/{id}", method = RequestMethod.GET)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
	public RestResult getByRechargeId(@PathVariable Integer id) {
		return new RestResult(FfanadStatus.S_OK, invoiceInfoService.getByRechargeId(id));
	}
}
